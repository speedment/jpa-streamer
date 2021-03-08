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
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.LongIntermediateOperationFactory;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class InternalLongIntermediateOperationFactory implements LongIntermediateOperationFactory {

    private static final Function<LongStream, LongStream> SORTED_FUNCTION = LongStream::sorted;
    private static final IntermediateOperation<LongStream, LongStream> SORTED = new StandardIntermediateOperation<>(
            IntermediateOperationType.SORTED,
            LongStream.class,
            LongStream.class,
            SORTED_FUNCTION);

    private static final Function<LongStream, LongStream> DISTINCT_FUNCTION = LongStream::distinct;
    private static final IntermediateOperation<LongStream, LongStream> DISTINCT = new StandardIntermediateOperation<>(
            IntermediateOperationType.DISTINCT,
            LongStream.class,
            LongStream.class,
            DISTINCT_FUNCTION);

    private static final Function<LongStream, Stream<Long>> BOXED_FUNCTION = LongStream::boxed;
    private static final IntermediateOperation<LongStream, Stream<Long>> BOXED = new StandardIntermediateOperation<>(
            IntermediateOperationType.BOXED,
            LongStream.class,
            Stream.class,
            BOXED_FUNCTION);

    private static final Function<LongStream, DoubleStream> AS_DOUBLE_STREAM_FUNCTION = LongStream::asDoubleStream;
    private static final IntermediateOperation<LongStream, DoubleStream> AS_DOUBLE_STREAM = new StandardIntermediateOperation<>(
            IntermediateOperationType.AS,
            LongStream.class,
            DoubleStream.class,
            AS_DOUBLE_STREAM_FUNCTION);


    @Override
    public IntermediateOperation<LongStream, LongStream> createFilter(LongPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<LongStream> function = s -> s.filter(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FILTER,
                LongStream.class,
                LongStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createMap(LongUnaryOperator mapper) {
        requireNonNull(mapper);
        final UnaryOperator<LongStream> function = s -> s.map(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO_SAME,
                LongStream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public <U> IntermediateOperation<LongStream, Stream<U>> createMapToObj(LongFunction<? extends U> mapper) {
        requireNonNull(mapper);
        final Function<LongStream, Stream<U>> function = s -> s.mapToObj(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                LongStream.class,
                Stream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<LongStream, IntStream> createMapToInt(LongToIntFunction mapper) {
        requireNonNull(mapper);
        final Function<LongStream, IntStream> function = s -> s.mapToInt(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                LongStream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<LongStream, DoubleStream> createMapToDouble(LongToDoubleFunction mapper) {
        requireNonNull(mapper);
        final Function<LongStream, DoubleStream> function = s -> s.mapToDouble(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                LongStream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createFlatMap(LongFunction<? extends LongStream> mapper) {
        requireNonNull(mapper);
        final UnaryOperator<LongStream> function = s -> s.flatMap(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO_SAME,
                LongStream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> acquireDistinct() {
        return DISTINCT;
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> acquireSorted() {
        return SORTED;
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createPeek(LongConsumer action) {
        requireNonNull(action);
        final UnaryOperator<LongStream> function = s -> s.peek(action);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.PEEK,
                LongStream.class,
                LongStream.class,
                function,
                action);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createLimit(long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));

        final UnaryOperator<LongStream> function = s -> s.limit(maxSize);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.LIMIT,
                LongStream.class,
                LongStream.class,
                function,
                maxSize);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createSkip(long n) {
        if (n < 0)
            throw new IllegalArgumentException(Long.toString(n));

        final UnaryOperator<LongStream> function = s -> s.skip(n);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.SKIP,
                LongStream.class,
                LongStream.class,
                function,
                n);

    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createTakeWhile(LongPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<LongStream> function = s -> Java9StreamUtil.takeWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.TAKE_WHILE,
                LongStream.class,
                LongStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createDropWhile(LongPredicate predicate) {
        requireNonNull(predicate);
        final UnaryOperator<LongStream> function = s -> Java9StreamUtil.dropWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.DROP_WHILE,
                LongStream.class,
                LongStream.class,
                function,
                predicate);
    }

    @Override
    public IntermediateOperation<LongStream, Stream<Long>> acquireBoxed() {
        return BOXED;
    }

    @Override
    public IntermediateOperation<LongStream, DoubleStream> acquireAsDoubleStream() {
        return AS_DOUBLE_STREAM;
    }
}
