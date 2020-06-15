package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.TerminatingOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.InternalStandardTerminatingOperationFactory;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StandardTerminatingOperationFactory implements TerminatingOperationFactory {

    private final TerminatingOperationFactory delegate = new InternalStandardTerminatingOperationFactory();

    @Override
    public <T> TerminatingOperation<Stream<T>, Void> createForEach(Consumer<? super T> action) {
        return delegate.createForEach(action);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Void> createForEachOrdered(Consumer<? super T> action) {
        return delegate.createForEachOrdered(action);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Object[]> createToArray() {
        return delegate.createToArray();
    }

    @Override
    public <T, A> TerminatingOperation<Stream<T>, A[]> createToArray(IntFunction<A[]> generator) {
        return delegate.createToArray(generator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, T> createReduce(T identity, BinaryOperator<T> accumulator) {
        return delegate.createReduce(identity, accumulator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createReduce(BinaryOperator<T> accumulator) {
        return delegate.createReduce(accumulator);
    }

    @Override
    public <T, U> TerminatingOperation<Stream<T>, U> createReduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return delegate.createReduce(identity, accumulator, combiner);
    }

    @Override
    public <T, R> TerminatingOperation<Stream<T>, R> createCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return delegate.createCollect(supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> TerminatingOperation<Stream<T>, R> createCollect(Collector<? super T, A, R> collector) {
        return delegate.createCollect(collector);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createMin(Comparator<? super T> comparator) {
        return delegate.createMin(comparator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createMax(Comparator<? super T> comparator) {
        return delegate.createMax(comparator);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Long> createCount() {
        return delegate.createCount();
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createAnyMatch(Predicate<? super T> predicate) {
        return delegate.createAnyMatch(predicate);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createAllMatch(Predicate<? super T> predicate) {
        return delegate.createAllMatch(predicate);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Boolean> createNoneMatch(Predicate<? super T> predicate) {
        return delegate.createNoneMatch(predicate);
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createFindFirst() {
        return delegate.createFindFirst();
    }

    @Override
    public <T> TerminatingOperation<Stream<T>, Optional<T>> createFindAny() {
        return delegate.createFindAny();
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminatingOperation<S, Iterator<T>> createIterator() {
        return delegate.createIterator();
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminatingOperation<S, Spliterator<T>> createSpliterator() {
        return delegate.createSpliterator();
    }
}