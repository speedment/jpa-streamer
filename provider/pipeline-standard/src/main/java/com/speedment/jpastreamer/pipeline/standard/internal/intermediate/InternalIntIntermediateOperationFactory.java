/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.pipeline.standard.internal.intermediate;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import com.speedment.jpastreamer.pipeline.intermediate.IntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;

public final class InternalIntIntermediateOperationFactory implements IntIntermediateOperationFactory {

    private static final Function<IntStream, IntStream> SORTED_FUNCTION = IntStream::sorted;
    private static final IntermediateOperation<IntStream, IntStream> SORTED = new StandardIntermediateOperation<>(
            IntermediateOperationType.SORTED,
            IntStream.class,
            IntStream.class,
            SORTED_FUNCTION);

    private static final Function<IntStream, IntStream> DISTINCT_FUNCTION = IntStream::distinct;
    private static final IntermediateOperation<IntStream, IntStream> DISTINCT = new StandardIntermediateOperation<>(
            IntermediateOperationType.DISTINCT,
            IntStream.class,
            IntStream.class,
            DISTINCT_FUNCTION);

    private static final Function<IntStream, Stream<Integer>> BOXED_FUNCTION = IntStream::boxed;
    private static final IntermediateOperation<IntStream, Stream<Integer>> BOXED = new StandardIntermediateOperation<>(
            IntermediateOperationType.BOXED,
            IntStream.class,
            Stream.class,
            BOXED_FUNCTION);

    private static final Function<IntStream, LongStream> AS_LONG_STREAM_FUNCTION = IntStream::asLongStream;
    private static final IntermediateOperation<IntStream, LongStream> AS_LONG_STREAM = new StandardIntermediateOperation<>(
            IntermediateOperationType.AS,
            IntStream.class,
            LongStream.class,
            AS_LONG_STREAM_FUNCTION);

    private static final Function<IntStream, DoubleStream> AS_DOUBLE_STREAM_FUNCTION = IntStream::asDoubleStream;
    private static final IntermediateOperation<IntStream, DoubleStream> AS_DOUBLE_STREAM = new StandardIntermediateOperation<>(
            IntermediateOperationType.AS,
            IntStream.class,
            DoubleStream.class,
            AS_DOUBLE_STREAM_FUNCTION);


    @Override
    public IntermediateOperation<IntStream, IntStream> createFilter(IntPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<IntStream> function = s -> s.filter(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FILTER,
                IntStream.class,
                IntStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createMap(IntUnaryOperator mapper) {
        requireNonNull(mapper);
        final UnaryOperator<IntStream> function = s -> s.map(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO_SAME,
                IntStream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public <U> IntermediateOperation<IntStream, Stream<U>> createMapToObj(IntFunction<? extends U> mapper) {
        requireNonNull(mapper);
        final Function<IntStream, Stream<U>> function = s -> s.mapToObj(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                IntStream.class,
                Stream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<IntStream, LongStream> createMapToLong(IntToLongFunction mapper) {
        requireNonNull(mapper);
        final Function<IntStream, LongStream> function = s -> s.mapToLong(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                IntStream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<IntStream, DoubleStream> createMapToDouble(IntToDoubleFunction mapper) {
        requireNonNull(mapper);
        final Function<IntStream, DoubleStream> function = s -> s.mapToDouble(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                IntStream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createFlatMap(IntFunction<? extends IntStream> mapper) {
        requireNonNull(mapper);
        final UnaryOperator<IntStream> function = s -> s.flatMap(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO_SAME,
                IntStream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> acquireDistinct() {
        return DISTINCT;
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> acquireSorted() {
        return SORTED;
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createPeek(IntConsumer action) {
        requireNonNull(action);
        final UnaryOperator<IntStream> function = s -> s.peek(action);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.PEEK,
                IntStream.class,
                IntStream.class,
                function,
                action);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createLimit(long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));

        final UnaryOperator<IntStream> function = s -> s.limit(maxSize);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.LIMIT,
                IntStream.class,
                IntStream.class,
                function,
                maxSize);

    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createSkip(long n) {
        if (n < 0)
            throw new IllegalArgumentException(Long.toString(n));

        final UnaryOperator<IntStream> function = s -> s.skip(n);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.SKIP,
                IntStream.class,
                IntStream.class,
                function,
                n);

    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createTakeWhile(IntPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<IntStream> function = s -> Java9StreamUtil.takeWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.TAKE_WHILE,
                IntStream.class,
                IntStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createDropWhile(IntPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<IntStream> function = s -> Java9StreamUtil.dropWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.DROP_WHILE,
                IntStream.class,
                IntStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<IntStream, Stream<Integer>> acquireBoxed() {
        return BOXED;
    }

    @Override
    public IntermediateOperation<IntStream, LongStream> acquireAsLongStream() {
        return AS_LONG_STREAM;
    }

    @Override
    public IntermediateOperation<IntStream, DoubleStream> acquireAsDoubleStream() {
        return AS_DOUBLE_STREAM;
    }
}
