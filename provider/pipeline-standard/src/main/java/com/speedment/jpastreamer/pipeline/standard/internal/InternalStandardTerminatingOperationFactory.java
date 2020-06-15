package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.TerminatingOperationFactory;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperationType;

import java.util.*;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class InternalStandardTerminatingOperationFactory implements TerminatingOperationFactory {

    @Override
    public <T> TerminatingOperation<Stream<T>, Void> createForEach(final Consumer<? super T> action) {
        requireNonNull(action);
        final Consumer<Stream<T>> function = (Stream<T> stream) -> stream.forEach(action);

        return new StandardTerminatingOperation<>(
                TerminatingOperationType.FOR_EACH,
                Stream.class,
                void.class,
                function,
                action);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Void> createForEachOrdered(final Consumer<? super T> action) {
        requireNonNull(action);
        final Consumer<Stream<T>> function = stream -> stream.forEachOrdered(action);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.FOR_EACH_ORDERED,
                Stream.class,
                void.class,
                function,
                action);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Object[]> createToArray() {
        final Function<Stream<T>, Object[]> function = Stream::toArray;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.TO_ARRAY,
                Stream.class,
                Object[].class,
                function);

    }

    @Override
    public <T, A> TerminatingOperation<Stream<T>, A[]> createToArray(final IntFunction<A[]> generator) {
        requireNonNull(generator);
        final Function<Stream<T>, A[]> function = (Stream<T> stream) -> stream.toArray(generator);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.TO_ARRAY,
                Stream.class,
                Object[].class,
                function,
                generator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, T> createReduce(final T identity, final BinaryOperator<T> accumulator) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        final Function<Stream<T>, T> function = stream -> stream.reduce(identity, accumulator);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.REDUCE,
                Stream.class,
                Object.class,
                function,
                identity, accumulator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createReduce(final BinaryOperator<T> accumulator) {
        requireNonNull(accumulator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.reduce(accumulator);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.REDUCE,
                Stream.class,
                Optional.class,
                function,
                accumulator);
    }

    @Override
    public <T, U> TerminatingOperation<Stream<T>, U> createReduce(final U identity,
                                                                  final BiFunction<U, ? super T, U> accumulator,
                                                                  final BinaryOperator<U> combiner) {
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        final Function<Stream<T>, U> function = stream -> stream.reduce(identity, accumulator, combiner);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.REDUCE,
                Stream.class,
                Object.class,
                function,
                identity, accumulator, combiner);
    }

    @Override
    public <T, R> TerminatingOperation<Stream<T>, R> createCollect(final Supplier<R> supplier,
                                                                   final BiConsumer<R, ? super T> accumulator,
                                                                   final BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        final Function<Stream<T>, R> function = stream -> stream.collect(supplier, accumulator, combiner);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.COLLECT,
                Stream.class,
                Object.class,
                function,
                supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> TerminatingOperation<Stream<T>, R> createCollect(final Collector<? super T, A, R> collector) {
        requireNonNull(collector);
        final Function<Stream<T>, R> function = stream -> stream.collect(collector);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.COLLECT,
                Stream.class,
                Object.class,
                function,
                collector);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createMin(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.min(comparator);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.MIN,
                Stream.class,
                Optional.class,
                function,
                comparator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createMax(final Comparator<? super T> comparator) {
        requireNonNull(comparator);
        final Function<Stream<T>, Optional<T>> function = stream -> stream.max(comparator);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.MAX,
                Stream.class,
                Optional.class,
                function,
                comparator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Long> createCount() {
        final ToLongFunction<Stream<T>> function = Stream::count;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.COUNT,
                Stream.class,
                long.class,
                function);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createAnyMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.anyMatch(predicate);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.ANY_MATCH,
                Stream.class,
                boolean.class,
                function);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createAllMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.allMatch(predicate);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.ALL_MATCH,
                Stream.class,
                boolean.class,
                function);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createNoneMatch(final Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final Predicate<Stream<T>> function = stream -> stream.noneMatch(predicate);
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.NONE_MATCH,
                Stream.class,
                boolean.class,
                function);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createFindFirst() {
        final Function<Stream<T>, Optional<T>> function = Stream::findFirst;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.FIND_FIRST,
                Stream.class,
                Optional.class,
                function);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createFindAny() {
        final Function<Stream<T>, Optional<T>> function = Stream::findAny;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.FIND_ANY,
                Stream.class,
                Optional.class,
                function);
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminatingOperation<S, Iterator<T>> createIterator() {
        final Function<Stream<T>, Iterator<T>> function = Stream::iterator;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.ITERATOR,
                BaseStream.class,
                Iterator.class,
                function);
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminatingOperation<S, Spliterator<T>> createSpliterator() {
        final Function<Stream<T>, Spliterator<T>> function = Stream::spliterator;
        return new StandardTerminatingOperation<>(
                TerminatingOperationType.SPLITERATOR,
                BaseStream.class,
                Spliterator.class,
                function);
    }
}