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
package com.speedment.jpastreamer.pipeline.terminal;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;

public interface DoubleTerminalOperationFactory extends BaseStreamTerminalOperationFactory {

    TerminalOperation<DoubleStream, Void> createForEach(DoubleConsumer action);

    TerminalOperation<DoubleStream, Void> createForEachOrdered(DoubleConsumer action);

    TerminalOperation<DoubleStream, double[]> acquireToArray();

    TerminalOperation<DoubleStream, Double> createReduce(double identity, DoubleBinaryOperator op);

    TerminalOperation<DoubleStream, OptionalDouble> createReduce(DoubleBinaryOperator op);

    <R> TerminalOperation<DoubleStream, R> createCollect(Supplier<R> supplier,
                                                         ObjDoubleConsumer<R> accumulator,
                                                         BiConsumer<R, R> combiner);

    TerminalOperation<DoubleStream, Double> acquireSum();

    TerminalOperation<DoubleStream, OptionalDouble> acquireMin();

    TerminalOperation<DoubleStream, OptionalDouble> acquireMax();

    TerminalOperation<DoubleStream, Long> acquireCount();


    TerminalOperation<DoubleStream, OptionalDouble> acquireAverage();

    TerminalOperation<DoubleStream, DoubleSummaryStatistics> acquireSummaryStatistics();


    TerminalOperation<DoubleStream, Boolean> createAnyMatch(DoublePredicate predicate);

    TerminalOperation<DoubleStream, Boolean> createAllMatch(DoublePredicate predicate);

    TerminalOperation<DoubleStream, Boolean> createNoneMatch(DoublePredicate predicate);

    TerminalOperation<DoubleStream, OptionalDouble> acquireFindFirst();

    TerminalOperation<DoubleStream, OptionalDouble> acquireFindAny();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<DoubleStream, PrimitiveIterator.OfDouble> acquireIterator();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<DoubleStream, Spliterator.OfDouble> acquireSpliterator();

}
