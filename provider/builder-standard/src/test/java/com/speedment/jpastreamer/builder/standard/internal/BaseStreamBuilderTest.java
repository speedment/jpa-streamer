/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.criteria.JoinType;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class BaseStreamBuilderTest<T, S extends BaseStream<T, S>> {

    protected static final Supplier<Stream<String>> SOURCE = () -> Stream.of("0", "1", "2", "3", "4", "0");

    private Renderer renderer;
    private StreamConfiguration<String> streamConfiguration;
    private BaseBuilderState<String> baseState;
    protected Stream<String> builder;
    protected AtomicInteger closeCounter;
    protected S stream;

    @BeforeEach
    void beforeEach() {
        renderer = new MockRenderer();
        streamConfiguration = new MockStreamConfiguration<>(String.class);
        baseState = new BaseBuilderState<>(InjectedFactories.INSTANCE, streamConfiguration, renderer);
        builder = new StreamBuilder<>(baseState);
        closeCounter = new AtomicInteger();
    }

    protected abstract S unboxed(Stream<String> stream);

    protected abstract long count(S stream);

    protected Stream<?> boxed(BaseStream<?, ?> stream) {
        if (stream instanceof Stream)
            return (Stream<?>) stream;
        if (stream instanceof IntStream)
            return ((IntStream) stream).boxed();
        if (stream instanceof LongStream)
            return ((LongStream) stream).boxed();
        if (stream instanceof DoubleStream)
            return ((DoubleStream) stream).boxed();

        throw new IllegalStateException();
    }

    protected void testIntermediate(final Function<S, BaseStream<?, ?>> intermediateOperation) {
        testTerminal(s -> boxed(intermediateOperation.apply(s)).collect(toList()));
    }

    protected <R> void testTerminal(final Function<S, R> terminalOperation) {
        testTerminal(terminalOperation, Objects::equals);
    }

    protected <R> void testTerminal(final Function<S, R> terminalOperation, final BiPredicate<R, R> equalPredicate) {
        final R expected = terminalOperation.apply(unboxed(SOURCE.get()));

        final S actualStream = unboxed(builder)
                .onClose(closeCounter::getAndIncrement);

        final R actual = terminalOperation.apply(actualStream);

        // Did we get the same result?
        final boolean success = equalPredicate.test(expected, actual);

        if (!success) {
            // Use assertEquals to report the problem in a user-friendly way
            assertEquals(expected, actual);
        }

        // Was the close handler invoked exactly one time?
        // assertEquals(1, closeCounter.get());


        // Check that we protect against re-use of already mapped stream
        assertThrows(IllegalStateException.class, () -> count(actualStream));
    }

    boolean equalsSummaryStatistics(final IntSummaryStatistics a, final IntSummaryStatistics b) {
        return Stream.<Function<IntSummaryStatistics, Number>>of(
                IntSummaryStatistics::getCount,
                IntSummaryStatistics::getSum,
                IntSummaryStatistics::getMin,
                IntSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }

    boolean equalsSummaryStatistics(final LongSummaryStatistics a, final LongSummaryStatistics b) {
        return Stream.<Function<LongSummaryStatistics, Number>>of(
                LongSummaryStatistics::getCount,
                LongSummaryStatistics::getSum,
                LongSummaryStatistics::getMin,
                LongSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }

    boolean equalsSummaryStatistics(final DoubleSummaryStatistics a, final DoubleSummaryStatistics b) {
        return Stream.<Function<DoubleSummaryStatistics, Number>>of(
                DoubleSummaryStatistics::getCount,
                DoubleSummaryStatistics::getSum,
                DoubleSummaryStatistics::getMin,
                DoubleSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }


    @SuppressWarnings("unchecked")
    private static final class MockRenderer implements Renderer {

        @Override
        public <E, T,  S extends BaseStream<T, S>> RenderResult<E, T, S> render(Pipeline<E> pipeline, StreamConfiguration<E> streamConfiguration) {

            // System.out.println(pipeline);

            return new MyRenderResult<>(
                    pipeline.root(),
                    (BaseStream) replay(SOURCE.get(), (Pipeline<String>) pipeline),
                    pipeline.terminatingOperation()
            );
        }

        @Override
        public void close() {
        }

        <T> BaseStream<?, ?> replay(Stream<T> initialStream, Pipeline<T> pipeLine) {
            BaseStream<?, ?> result = initialStream;
            for (IntermediateOperation intermediateOperation : pipeLine.intermediateOperations()) {
                result = (BaseStream<?, ?>) intermediateOperation.function().apply(result);
            }
            return result;
        }

    }

    private static final class MyRenderResult<E, T, S extends BaseStream<T, S>> implements RenderResult<E, T, S> {

        private final Class<E> root;
        private final S stream;
        private final TerminalOperation<?, ?> terminalOperation;

        MyRenderResult(
                final Class<E> returnType,
                final S stream,
                final TerminalOperation<?, ?> terminalOperation
        ) {
            this.stream = stream;
            this.root = returnType;
            this.terminalOperation = terminalOperation;
        }

        @Override
        public Class<E> root() {
            return root;
        }

        @Override
        public S stream() {
            return stream;
        }

        @Override
        public TerminalOperation<?, ?> terminalOperation() {
            return terminalOperation;
        }
    }

    private static final class MockStreamConfiguration<T> implements StreamConfiguration<T> {

        private final Class<T> entityClass;
        private final Projection<T> projection;

        private MockStreamConfiguration(Class<T> entityClass) {
            this.entityClass = entityClass;
            this.projection = null;
        }

        @Override
        public Class<T> entityClass() {
            return entityClass;
        }

        @Override
        public Set<JoinConfiguration<T>> joins() {
            return new HashSet<>();
        }

        @Override
        public StreamConfiguration<T> joining(Field<T> field, JoinType joinType) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Projection<T>> selections() {
            return Optional.ofNullable(projection);
        }

        @Override
        public StreamConfiguration<T> selecting(Projection<T> projection) {
            throw new UnsupportedOperationException();
        }
    }

}
