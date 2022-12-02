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
package com.speedment.jpastreamer.pipeline.standard.terminal;

import com.speedment.jpastreamer.pipeline.standard.internal.terminal.InternalIntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.IntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

public class StandardIntTerminalOperationFactory implements IntTerminalOperationFactory {

    private final IntTerminalOperationFactory delegate = new InternalIntTerminalOperationFactory();

    @Override
    public TerminalOperation<IntStream, Void> createForEach(IntConsumer action) {
        return delegate.createForEach(action);
    }

    @Override
    public TerminalOperation<IntStream, Void> createForEachOrdered(IntConsumer action) {
        return delegate.createForEachOrdered(action);
    }

    @Override
    public TerminalOperation<IntStream, int[]> acquireToArray() {
        return delegate.acquireToArray();
    }

    @Override
    public TerminalOperation<IntStream, Integer> createReduce(int identity, IntBinaryOperator op) {
        return delegate.createReduce(identity, op);
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> createReduce(IntBinaryOperator op) {
        return delegate.createReduce(op);
    }

    @Override
    public <R> TerminalOperation<IntStream, R> createCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return delegate.createCollect(supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<IntStream, Integer> acquireSum() {
        return delegate.acquireSum();
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireMin() {
        return delegate.acquireMin();
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireMax() {
        return delegate.acquireMax();
    }

    @Override
    public TerminalOperation<IntStream, Long> acquireCount() {
        return delegate.acquireCount();
    }

    @Override
    public TerminalOperation<IntStream, OptionalDouble> acquireAverage() {
        return delegate.acquireAverage();
    }

    @Override
    public TerminalOperation<IntStream, IntSummaryStatistics> acquireSummaryStatistics() {
        return delegate.acquireSummaryStatistics();
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createAnyMatch(IntPredicate predicate) {
        return delegate.createAnyMatch(predicate);
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createAllMatch(IntPredicate predicate) {
        return delegate.createAllMatch(predicate);
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createNoneMatch(IntPredicate predicate) {
        return delegate.createNoneMatch(predicate);
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireFindFirst() {
        return delegate.acquireFindFirst();
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireFindAny() {
        return delegate.acquireFindAny();
    }

    @Override
    public TerminalOperation<IntStream, PrimitiveIterator.OfInt> acquireIterator() {
        return delegate.acquireIterator();
    }

    @Override
    public TerminalOperation<IntStream, Spliterator.OfInt> acquireSpliterator() {
        return delegate.acquireSpliterator();
    }

}
