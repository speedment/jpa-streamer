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
package com.speedment.jpastreamer.pipeline.standard.internal.intermediate;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;

public class InternalIntermediateOperationFactory implements IntermediateOperationFactory {

    private static final Function<Stream<Object>, Stream<Object>> SORTED_FUNCTION = Stream::sorted;
    private static final IntermediateOperation<Stream<Object>, Stream<Object>> SORTED = new StandardIntermediateOperation<>(
            IntermediateOperationType.SORTED,
            Stream.class,
            Stream.class,
            SORTED_FUNCTION);

    private static final Function<Stream<Object>, Stream<Object>> DISTINCT_FUNCTION = Stream::distinct;
    private static final IntermediateOperation<Stream<Object>, Stream<Object>> DISTINCT = new StandardIntermediateOperation<>(
            IntermediateOperationType.DISTINCT,
            Stream.class,
            Stream.class,
            DISTINCT_FUNCTION);

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createFilter(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final UnaryOperator<Stream<T>> function = s -> s.filter(predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FILTER,
                Stream.class,
                Stream.class,
                function,
                predicate);

    }

    @Override
    public <T, R> IntermediateOperation<Stream<T>, Stream<R>> createMap(final Function<? super T, ? extends R> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, Stream<R>> function = s -> s.map(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP,
                Stream.class,
                Stream.class,
                function,
                mapper);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, IntStream> createMapToInt(final ToIntFunction<? super T> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, IntStream> function = s -> s.mapToInt(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                Stream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, LongStream> createMapToLong(final ToLongFunction<? super T> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, LongStream> function = s -> s.mapToLong(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                Stream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, DoubleStream> createMapToDouble(final ToDoubleFunction<? super T> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, DoubleStream> function = s -> s.mapToDouble(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.MAP_TO,
                Stream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public <T, R> IntermediateOperation<Stream<T>, Stream<R>> createFlatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, Stream<R>> function = s -> s.flatMap(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP,
                Stream.class,
                Stream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, IntStream> createFlatMapToInt(final Function<? super T, ? extends IntStream> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, IntStream> function = s -> s.flatMapToInt(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO,
                Stream.class,
                IntStream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, LongStream> createFlatMapToLong(final Function<? super T, ? extends LongStream> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, LongStream> function = s -> s.flatMapToLong(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO,
                Stream.class,
                LongStream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, DoubleStream> createFlatMapToDouble(final Function<? super T, ? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        final Function<Stream<T>, DoubleStream> function = s -> s.flatMapToDouble(mapper);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.FLAT_MAP_TO,
                Stream.class,
                DoubleStream.class,
                function,
                mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> acquireDistinct() {
        return typed(DISTINCT);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> acquireSorted() {
        return typed(SORTED);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createSorted(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        final UnaryOperator<Stream<T>> function = s -> s.sorted(comparator);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.SORTED,
                Stream.class,
                Stream.class,
                function,
                comparator);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createPeek(final Consumer<? super T> action) {
        requireNonNull(action);
        final UnaryOperator<Stream<T>> function = s -> s.peek(action);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.PEEK,
                Stream.class,
                Stream.class,
                function,
                action);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createLimit(final long maxSize) {
        if (maxSize < 0)
            throw new IllegalArgumentException(Long.toString(maxSize));

        final UnaryOperator<Stream<T>> function = s -> s.limit(maxSize);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.LIMIT,
                Stream.class,
                Stream.class,
                function,
                maxSize);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createSkip(final long n) {
        if (n < 0)
            throw new IllegalArgumentException(Long.toString(n));

        final UnaryOperator<Stream<T>> function = s -> s.skip(n);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.SKIP,
                Stream.class,
                Stream.class,
                function,
                n);

    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createTakeWhile(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final UnaryOperator<Stream<T>> function = s -> Java9StreamUtil.takeWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.TAKE_WHILE,
                Stream.class,
                Stream.class,
                function,
                predicate);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createDropWhile(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final UnaryOperator<Stream<T>> function = s -> Java9StreamUtil.dropWhile(s, predicate);
        return new StandardIntermediateOperation<>(
                IntermediateOperationType.DROP_WHILE,
                Stream.class,
                Stream.class,
                function,
                predicate);
    }

    @SuppressWarnings("unchecked")
    private <S extends BaseStream<?, S>, R extends BaseStream<?, R>> IntermediateOperation<S, R> typed(final IntermediateOperation<?, ?> terminalOperation) {
        return (IntermediateOperation<S, R>) terminalOperation;
    }

}