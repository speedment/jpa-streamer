/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.pipeline.intermediate.standard.internal;

import com.speedment.jpastreamer.pipeline.intermediate.DoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class InternalDoubleIntermediateOperationFactory implements DoubleIntermediateOperationFactory {

    private static final Function<DoubleStream, DoubleStream> SORTED_FUNCTION = DoubleStream::sorted;
    private static final IntermediateOperation<DoubleStream, DoubleStream> SORTED = new StandardIntermediateOperation<>(
            IntermediateOperationType.SORTED,
            DoubleStream.class,
            DoubleStream.class,
            SORTED_FUNCTION);

    private static final Function<DoubleStream, DoubleStream> DISTINCT_FUNCTION = DoubleStream::distinct;
    private static final IntermediateOperation<DoubleStream, DoubleStream> DISTINCT = new StandardIntermediateOperation<>(
            IntermediateOperationType.DISTINCT,
            DoubleStream.class,
            DoubleStream.class,
            DISTINCT_FUNCTION);


    private static final Function<DoubleStream, Stream<Double>> BOXED_FUNCTION = DoubleStream::boxed;
    private static final IntermediateOperation<DoubleStream, Stream<Double>> BOXED = new StandardIntermediateOperation<>(
            IntermediateOperationType.BOXED,
            DoubleStream.class,
            Stream.class,
            BOXED_FUNCTION);

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createFilter(DoublePredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<DoubleStream> function = s -> s.filter(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FILTER,
                DoubleStream.class,
                DoubleStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createMap(DoubleUnaryOperator mapper) {
        requireNonNull(mapper);
        final UnaryOperator<DoubleStream> function = s -> s.map(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO_SAME,
                DoubleStream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public <U> IntermediateOperation<DoubleStream, Stream<U>> createMapToObj(DoubleFunction<? extends U> mapper) {
        requireNonNull(mapper);
        final Function<DoubleStream, Stream<U>> function = s -> s.mapToObj(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                DoubleStream.class,
                Stream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, LongStream> createMapToLong(DoubleToLongFunction mapper) {
        requireNonNull(mapper);
        final Function<DoubleStream, LongStream> function = s -> s.mapToLong(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                DoubleStream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, IntStream> createMapToInt(DoubleToIntFunction mapper) {
        requireNonNull(mapper);
        final Function<DoubleStream, IntStream> function = s -> s.mapToInt(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                DoubleStream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createFlatMap(DoubleFunction<? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        final UnaryOperator<DoubleStream> function = s -> s.flatMap(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO_SAME,
                DoubleStream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> acquireDistinct() {
        return TypeUtil.typed(DISTINCT);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> acquireSorted() {
        return TypeUtil.typed(SORTED);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createPeek(DoubleConsumer action) {
        requireNonNull(action);
        final UnaryOperator<DoubleStream> function = s -> s.peek(action);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.PEEK,
                DoubleStream.class,
                DoubleStream.class,
                function,
                action);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createLimit(long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));

        final UnaryOperator<DoubleStream> function = s -> s.limit(maxSize);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.LIMIT,
                DoubleStream.class,
                DoubleStream.class,
                function,
                maxSize);

    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createSkip(long n) {
        if (n < 0)
            throw new IllegalArgumentException(Long.toString(n));

        final UnaryOperator<DoubleStream> function = s -> s.skip(n);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.SKIP,
                DoubleStream.class,
                DoubleStream.class,
                function,
                n);

    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createTakeWhile(DoublePredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<DoubleStream> function = s -> s.takeWhile(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.TAKE_WHILE,
                DoubleStream.class,
                DoubleStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createDropWhile(DoublePredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<DoubleStream> function = s -> s.dropWhile(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.DROP_WHILE,
                DoubleStream.class,
                DoubleStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, Stream<Double>> acquireBoxed() {
        return BOXED;
    }
}
