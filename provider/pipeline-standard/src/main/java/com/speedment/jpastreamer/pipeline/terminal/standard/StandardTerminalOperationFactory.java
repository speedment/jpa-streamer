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
package com.speedment.jpastreamer.pipeline.terminal.standard;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.internal.InternalTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StandardTerminalOperationFactory implements TerminalOperationFactory {

    private final TerminalOperationFactory delegate = new InternalTerminalOperationFactory();

    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEach(Consumer<? super T> action) {
        return delegate.createForEach(action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Void> createForEachOrdered(Consumer<? super T> action) {
        return delegate.createForEachOrdered(action);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Object[]> acquireToArray() {
        return delegate.acquireToArray();
    }

    @Override
    public <T, A> TerminalOperation<Stream<T>, A[]> createToArray(IntFunction<A[]> generator) {
        return delegate.createToArray(generator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, T> createReduce(T identity, BinaryOperator<T> accumulator) {
        return delegate.createReduce(identity, accumulator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createReduce(BinaryOperator<T> accumulator) {
        return delegate.createReduce(accumulator);
    }

    @Override
    public <T, U> TerminalOperation<Stream<T>, U> createReduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return delegate.createReduce(identity, accumulator, combiner);
    }

    @Override
    public <T, R> TerminalOperation<Stream<T>, R> createCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return delegate.createCollect(supplier, accumulator, combiner);
    }

    @Override
    public <T, R, A> TerminalOperation<Stream<T>, R> createCollect(Collector<? super T, A, R> collector) {
        return delegate.createCollect(collector);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMin(Comparator<? super T> comparator) {
        return delegate.createMin(comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> createMax(Comparator<? super T> comparator) {
        return delegate.createMax(comparator);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Long> acquireCount() {
        return delegate.acquireCount();
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAnyMatch(Predicate<? super T> predicate) {
        return delegate.createAnyMatch(predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createAllMatch(Predicate<? super T> predicate) {
        return delegate.createAllMatch(predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Boolean> createNoneMatch(Predicate<? super T> predicate) {
        return delegate.createNoneMatch(predicate);
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindFirst() {
        return delegate.acquireFindFirst();
    }

    @Override
    public <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindAny() {
        return delegate.acquireFindAny();
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Iterator<T>> acquireIterator() {
        return delegate.acquireIterator();
    }

    @Override
    public <T, S extends BaseStream<T, S>> TerminalOperation<S, Spliterator<T>> acquireSpliterator() {
        return delegate.acquireSpliterator();
    }
}
