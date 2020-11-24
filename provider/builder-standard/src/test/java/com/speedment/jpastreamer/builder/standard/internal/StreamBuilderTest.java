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

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.JoinType;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

class StreamBuilderTest {

    private static final Factories FACTORIES = InjectedFactories.INSTANCE;

    @Test
    void filter() {
        final Renderer renderer = new MockRenderer();
        final StreamConfiguration<String> streamConfiguration = new MockStreamConfiguration<>(String.class);
        Stream<String> builder = new StreamBuilder<>(FACTORIES, streamConfiguration, renderer);

        final long count = builder
                .skip(1)
                .limit(1)
                .count();

        System.out.println("count = " + count);

    }

    @Test
    void first() {
        final Renderer renderer = new MockRenderer();
        final StreamConfiguration<String> streamConfiguration = new MockStreamConfiguration<>(String.class);
        Stream<String> builder = new StreamBuilder<>(FACTORIES, streamConfiguration, renderer);

        final Optional<String> first = builder
                .filter(s -> s.length() > 0)
                .limit(1)
                .findFirst();

        System.out.println("`first` = " + first);

    }

    @Test
    void consumed() {
        assertThrows(IllegalStateException.class, () -> {
            final Renderer renderer = new MockRenderer();
            final StreamConfiguration<String> streamConfiguration = new MockStreamConfiguration<>(String.class);
            Stream<String> builder = new StreamBuilder<>(FACTORIES, streamConfiguration, renderer);

            final Optional<String> first = builder
                    .filter(s -> s.length() > 0)
                    .limit(1)
                    .findFirst();

            // We have already consumed the Stream so
            // this should not work.
            final Stream<Integer> illegal = builder.map(String::length);

        });
    }

    private static final class MockRenderer implements Renderer {

        private final Supplier<Stream<String>> source = () -> Stream.of("A", "B", "C");

        // Only works for Stream and not IntStream, etc.

        @Override
        public <T> RenderResult<T> render(Pipeline<T> pipeline, StreamConfiguration<T> streamConfiguration) {

            System.out.println(pipeline);

            return new MyRenderResult<>(
                pipeline.root(),
                (Stream<T>) replay(source.get(), (Pipeline<String>)pipeline),
                pipeline.terminatingOperation()
            );
        }

        @Override
        public void close() {
        }

        <T> BaseStream<?, ?> replay(Stream<T> initialStream, Pipeline<T> pipeLine) {
            BaseStream<?, ?> result = initialStream;
            for (IntermediateOperation intermediateOperation:pipeLine.intermediateOperations()) {
                result = (BaseStream<?,?>)intermediateOperation.function().apply(result);
            }
            return result;
        }

    }

    private static final class MyRenderResult<T> implements RenderResult<T> {

        private final Class<T> returnType;
        private final Stream<T> stream;
        private final TerminalOperation<?, ?> terminalOperation;

        public MyRenderResult(
            final Class<T> returnType,
            final Stream<T> stream,
            final TerminalOperation<?, ?> terminalOperation
        ) {
            this.stream = stream;
            this.returnType = returnType;
            this.terminalOperation = terminalOperation;
        }


        @Override
        public Class<T> root() {
            return returnType;
        }

        @Override
        public Stream<T> stream() {
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
        public Optional<Projection<T>> select() {
            return Optional.ofNullable(projection);
        }

        @Override
        public StreamConfiguration<T> select(Projection<T> projection) {
            throw new UnsupportedOperationException();
        }
    }

}
