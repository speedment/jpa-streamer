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

    private static final Function<Stream<Object>, Object[]> TO_ARRAY_FUNCTION = Stream::toArray;
    private static final TerminalOperation<Stream<Object>, Object[]> TO_ARRAY = new StandardTerminalOperation<>(
            TerminalOperationType.TO_ARRAY,
            Stream.class,
            Object[].class,
            TO_ARRAY_FUNCTION);

    private static final Function<Stream<Object>, Object> FIND_FIRST_FUNCTION = Stream::findFirst;
    private static final TerminalOperation<Stream<Object>, Optional<?>> FIND_FIRST = new StandardTerminalOperation<>(
            TerminalOperationType.FIND_FIRST,
            Stream.class,
            Optional.class,
            FIND_FIRST_FUNCTION);

    private static final Function<Stream<Object>, Object> FIND_ANY_FUNCTION = Stream::findAny;
    private static final TerminalOperation<Stream<Object>, Optional<?>> FIND_ANY = new StandardTerminalOperation<>(
            TerminalOperationType.FIND_ANY,
            Stream.class,
            Optional.class,
            FIND_ANY_FUNCTION);

    private static final Function<Stream<Object>, Object> COUNT_FUNCTION = Stream::count;
    private static final TerminalOperation<Stream<Object>, Long> COUNT = new StandardTerminalOperation<>(
            TerminalOperationType.COUNT,
            Stream.class,
            long.class,
            COUNT_FUNCTION);

    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEach(final Consumer<? super T> action) {
        requireNonNull(action);
        final Consumer<Stream<T>> function = (Stream<T> stream) -> stream.forEach(action);

        return new StandardTerminalOperation<>(
                TerminalOperationType.FOR_EACH,
                Stream.class,
                void.class,
                function,
                action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEachOrdered(final Consumer<? super T> action) {
        requireNonNull(action);
        final Consumer<Stream<T>> function = stream -> stream.forEachOrdered(action);
        return new StandardTerminalOperation<>(
                TerminalOperationType.FOR_EACH_ORDERED,
                Stream.class,
                void.class,
                function,
                action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Object[]> acquireToArray() {
        return typed(TO_ARRAY);
    }

    @Override
    public <T, A> TerminalOperation<Stream<T>, A[]> createToArray(final IntFunction<A[]> generator) {
        requireNonNull(generator);
        final Function<Stream<T>, A[]> function = (Stream<T> stream) -> stream.toArray(generator);
        return new StandardTerminalOperation<>(
                TerminalOperationType.TO_ARRAY,
                Stream.class,
                Object[].class,
                function,
                generator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, T> createReduce(final T identity, final BinaryOperator<T> accumulator) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        final Function<Stream<T>, T> function = stream -> stream.reduce(identity, accumulator);
        return new StandardTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Object.class,
                function,
                identity, accumulator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createReduce(final BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.reduce(accumulator);
        return new StandardTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Optional.class,
                function,
                accumulator);
    }

    @Override
    public <T, U> TerminalOperation<Stream<T>, U> createReduce(final U identity,
                                                               final BiFunction<U, ? super T, U> accumulator,
                                                               final BinaryOperator<U> combiner) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        final Function<Stream<T>, U> function = stream -> stream.reduce(identity, accumulator, combiner);
        return new StandardTerminalOperation<>(
                TerminalOperationType.REDUCE,
                Stream.class,
                Object.class,
                function,
                identity, accumulator, combiner);
    }

    @Override
    public <T, R> TerminalOperation<Stream<T>, R> createCollect(final Supplier<R> supplier,
                                                                final BiConsumer<R, ? super T> accumulator,
                                                                final BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        final Function<Stream<T>, R> function = stream -> stream.collect(supplier, accumulator, combiner);
        return new StandardTerminalOperation<>(
                TerminalOperationType.COLLECT,
                Stream.class,
                Object.class,
                function,
                supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> TerminalOperation<Stream<T>, R> createCollect(final Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        final Function<Stream<T>, R> function = stream -> stream.collect(collector);
        return new StandardTerminalOperation<>(
                TerminalOperationType.COLLECT,
                Stream.class,
                Object.class,
                function,
                collector);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMin(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.min(comparator);
        return new StandardTerminalOperation<>(
                TerminalOperationType.MIN,
                Stream.class,
                Optional.class,
                function,
                comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMax(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.max(comparator);
        return new StandardTerminalOperation<>(
                TerminalOperationType.MAX,
                Stream.class,
                Optional.class,
                function,
                comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Long> acquireCount() {
        return typed(COUNT);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAnyMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.anyMatch(predicate);
        return new StandardTerminalOperation<>(
                TerminalOperationType.ANY_MATCH,
                Stream.class,
                boolean.class,
                function);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAllMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.allMatch(predicate);
        return new StandardTerminalOperation<>(
                TerminalOperationType.ALL_MATCH,
                Stream.class,
                boolean.class,
                function);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createNoneMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.noneMatch(predicate);
        return new StandardTerminalOperation<>(
                TerminalOperationType.NONE_MATCH,
                Stream.class,
                boolean.class,
                function);
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
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Iterator<T>> createIterator() {
        final Function<Stream<T>, Iterator<T>> function = Stream::iterator;
        return new StandardTerminalOperation<>(
                TerminalOperationType.ITERATOR,
                BaseStream.class,
                Iterator.class,
                function);
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Spliterator<T>> createSpliterator() {
        final Function<Stream<T>, Spliterator<T>> function = Stream::spliterator;
        return new StandardTerminalOperation<>(
                TerminalOperationType.SPLITERATOR,
                BaseStream.class,
                Spliterator.class,
                function);
    }


    @SuppressWarnings("unchecked")
    private <S extends BaseStream<?, S>, R> TerminalOperation<S, R> typed(final TerminalOperation<?, ?> terminalOperation) {
        return (TerminalOperation<S, R>) (TerminalOperation<?, ?>) terminalOperation;
    }

}