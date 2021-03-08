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
package com.speedment.jpastreamer.pipeline.standard.terminal;

import com.speedment.jpastreamer.pipeline.standard.internal.terminal.InternalDoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.DoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;

public class StandardDoubleTerminalOperationFactory implements DoubleTerminalOperationFactory {

    private final DoubleTerminalOperationFactory delegate = new InternalDoubleTerminalOperationFactory();

    @Override
    public TerminalOperation<DoubleStream, Void> createForEach(DoubleConsumer action) {
        return delegate.createForEach(action);
    }

    @Override
    public TerminalOperation<DoubleStream, Void> createForEachOrdered(DoubleConsumer action) {
        return delegate.createForEachOrdered(action);
    }

    @Override
    public TerminalOperation<DoubleStream, double[]> acquireToArray() {
        return delegate.acquireToArray();
    }

    @Override
    public TerminalOperation<DoubleStream, Double> createReduce(double identity, DoubleBinaryOperator op) {
        return delegate.createReduce(identity, op);
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> createReduce(DoubleBinaryOperator op) {
        return delegate.createReduce(op);
    }

    @Override
    public <R> TerminalOperation<DoubleStream, R> createCollect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return delegate.createCollect(supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<DoubleStream, Double> acquireSum() {
        return delegate.acquireSum();
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireMin() {
        return delegate.acquireMin();
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireMax() {
        return delegate.acquireMax();
    }

    @Override
    public TerminalOperation<DoubleStream, Long> acquireCount() {
        return delegate.acquireCount();
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireAverage() {
        return delegate.acquireAverage();
    }

    @Override
    public TerminalOperation<DoubleStream, DoubleSummaryStatistics> acquireSummaryStatistics() {
        return delegate.acquireSummaryStatistics();
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createAnyMatch(DoublePredicate predicate) {
        return delegate.createAnyMatch(predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createAllMatch(DoublePredicate predicate) {
        return delegate.createAllMatch(predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createNoneMatch(DoublePredicate predicate) {
        return delegate.createNoneMatch(predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireFindFirst() {
        return delegate.acquireFindFirst();
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireFindAny() {
        return delegate.acquireFindAny();
    }

    @Override
    public TerminalOperation<DoubleStream, PrimitiveIterator.OfDouble> acquireIterator() {
        return delegate.acquireIterator();
    }

    @Override
    public TerminalOperation<DoubleStream, Spliterator.OfDouble> acquireSpliterator() {
        return delegate.acquireSpliterator();
    }
}
