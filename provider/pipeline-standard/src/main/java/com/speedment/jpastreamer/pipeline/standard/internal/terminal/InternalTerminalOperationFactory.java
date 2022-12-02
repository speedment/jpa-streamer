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
package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class InternalTerminalOperationFactory implements TerminalOperationFactory {

    private static final TerminalOperation<Stream<Object>, Object[]> TO_ARRAY = new ApplyTerminalOperation<>(
            TerminalOperationType.TO_ARRAY,
            Stream.class,
            Object[].class,
            Stream::toArray);

    private static final TerminalOperation<Stream<Object>, Optional<?>> FIND_FIRST = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_FIRST,
            Stream.class,
            Optional.class,
            Stream::findFirst);

    private static final TerminalOperation<Stream<Object>, Optional<?>> FIND_ANY = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_ANY,
            Stream.class,
            Optional.class,
            Stream::findAny);

    private static final TerminalOperation<Stream<Object>, Long> COUNT = new ApplyAsLongTerminalOperation<>(
            TerminalOperationType.COUNT,
            Stream.class,
            long.class,
            Stream::count);

    private static final TerminalOperation<Stream<Object>, Iterator<Object>> ITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.ITERATOR,
            Stream.class,
            Iterator.class,
            Stream::iterator);

    private static final TerminalOperation<Stream<Object>, Spliterator<Object>> SPLITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.SPLITERATOR,
            Stream.class,
            Spliterator.class,
            Stream::spliterator);


    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEach(final Consumer<? super T> action) {
        requireNonNull(action);

        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH,
                Stream.class,
                void.class,
                stream -> stream.forEach(action),
                action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEachOrdered(final Consumer<? super T> action) {
        requireNonNull(action);
        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH_ORDERED,
                Stream.class,
                void.class,
                stream -> stream.forEachOrdered(action),
                action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Object[]> acquireToArray() {
        return typed(TO_ARRAY);
    }

    @Override
    public <T, A> TerminalOperation<Stream<T>, A[]> createToArray(final IntFunction<A[]> generator) {
        requireNonNull(generator);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.TO_ARRAY,
                Stream.class,
                Object[].class,
                (Stream<T> stream) -> stream.toArray(generator),
                generator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, T> createReduce(final T identity, final BinaryOperator<T> accumulator) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Object.class,
                stream -> stream.reduce(identity, accumulator),
                identity, accumulator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createReduce(final BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Optional.class,
                stream -> stream.reduce(accumulator),
                accumulator);
    }

    @Override
    public <T, U> TerminalOperation<Stream<T>, U> createReduce(final U identity,
                                                               final BiFunction<U, ? super T, U> accumulator,
                                                               final BinaryOperator<U> combiner) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Object.class,
                stream -> stream.reduce(identity, accumulator, combiner),
                identity, accumulator, combiner);
    }

    @Override
    public <T, R> TerminalOperation<Stream<T>, R> createCollect(final Supplier<R> supplier,
                                                                final BiConsumer<R, ? super T> accumulator,
                                                                final BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.COLLECT,
                Stream.class,
                Object.class,
                stream -> stream.collect(supplier, accumulator, combiner),
                supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> TerminalOperation<Stream<T>, R> createCollect(final Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.COLLECT,
                Stream.class,
                Object.class,
                stream -> stream.collect(collector),
                collector);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMin(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.MIN,
                Stream.class,
                Optional.class,
                stream -> stream.min(comparator),
                comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMax(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.MAX,
                Stream.class,
                Optional.class,
                stream -> stream.max(comparator),
                comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Long> acquireCount() {
        return typed(COUNT);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAnyMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ANY_MATCH,
                Stream.class,
                boolean.class,
                stream -> stream.anyMatch(predicate),
                predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAllMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ALL_MATCH,
                Stream.class,
                boolean.class,
                stream -> stream.allMatch(predicate),
                predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createNoneMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.NONE_MATCH,
                Stream.class,
                boolean.class,
                stream -> stream.noneMatch(predicate),
                predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindFirst() {
        return typed(FIND_FIRST);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindAny() {
        return typed(FIND_ANY);
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Iterator<T>> acquireIterator() {
        return typed(ITERATOR);
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Spliterator<T>> acquireSpliterator() {
        return typed(SPLITERATOR);
    }

    @SuppressWarnings("unchecked")
    private <S extends BaseStream<?, S>, R> TerminalOperation<S, R> typed(final TerminalOperation<?, ?> terminalOperation) {
        return (TerminalOperation<S, R>) terminalOperation;
    }

}
