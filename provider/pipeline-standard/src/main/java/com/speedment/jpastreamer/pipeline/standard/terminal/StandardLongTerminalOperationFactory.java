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

import com.speedment.jpastreamer.pipeline.standard.internal.terminal.InternalLongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.LongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

public class StandardLongTerminalOperationFactory implements LongTerminalOperationFactory {

    private final LongTerminalOperationFactory delegate = new InternalLongTerminalOperationFactory();

    @Override
    public TerminalOperation<LongStream, Void> createForEach(LongConsumer action) {
        return delegate.createForEach(action);
    }

    @Override
    public TerminalOperation<LongStream, Void> createForEachOrdered(LongConsumer action) {
        return delegate.createForEachOrdered(action);
    }

    @Override
    public TerminalOperation<LongStream, long[]> acquireToArray() {
        return delegate.acquireToArray();
    }

    @Override
    public TerminalOperation<LongStream, Long> createReduce(long identity, LongBinaryOperator op) {
        return delegate.createReduce(identity, op);
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> createReduce(LongBinaryOperator op) {
        return delegate.createReduce(op);
    }

    @Override
    public <R> TerminalOperation<LongStream, R> createCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return delegate.createCollect(supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<LongStream, Long> acquireSum() {
        return delegate.acquireSum();
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireMin() {
        return delegate.acquireMin();
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireMax() {
        return delegate.acquireMax();
    }

    @Override
    public TerminalOperation<LongStream, Long> acquireCount() {
        return delegate.acquireCount();
    }

    @Override
    public TerminalOperation<LongStream, OptionalDouble> acquireAverage() {
        return delegate.acquireAverage();
    }

    @Override
    public TerminalOperation<LongStream, LongSummaryStatistics> acquireSummaryStatistics() {
        return delegate.acquireSummaryStatistics();
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createAnyMatch(LongPredicate predicate) {
        return delegate.createAnyMatch(predicate);
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createAllMatch(LongPredicate predicate) {
        return delegate.createAllMatch(predicate);
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createNoneMatch(LongPredicate predicate) {
        return delegate.createNoneMatch(predicate);
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireFindFirst() {
        return delegate.acquireFindFirst();
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireFindAny() {
        return delegate.acquireFindAny();
    }

    @Override
    public TerminalOperation<LongStream, PrimitiveIterator.OfLong> acquireIterator() {
        return delegate.acquireIterator();
    }

    @Override
    public TerminalOperation<LongStream, Spliterator.OfLong> acquireSpliterator() {
        return delegate.acquireSpliterator();
    }

}