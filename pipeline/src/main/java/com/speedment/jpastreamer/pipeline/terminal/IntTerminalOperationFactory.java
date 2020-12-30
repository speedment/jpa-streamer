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
package com.speedment.jpastreamer.pipeline.terminal;

import java.util.*;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;

public interface IntTerminalOperationFactory extends BaseStreamTerminalOperationFactory {

    TerminalOperation<IntStream, Void> createForEach(IntConsumer action);

    TerminalOperation<IntStream, Void> createForEachOrdered(IntConsumer action);

    TerminalOperation<IntStream, int[]> acquireToArray();

    TerminalOperation<IntStream, Integer> createReduce(int identity, IntBinaryOperator op);

    TerminalOperation<IntStream, OptionalInt> createReduce(IntBinaryOperator op);

    <R> TerminalOperation<IntStream, R> createCollect(Supplier<R> supplier,
                                                      ObjIntConsumer<R> accumulator,
                                                      BiConsumer<R, R> combiner);

    TerminalOperation<IntStream, Integer> acquireSum();

    TerminalOperation<IntStream, OptionalInt> acquireMin();

    TerminalOperation<IntStream, OptionalInt> acquireMax();

    TerminalOperation<IntStream, Long> acquireCount();


    TerminalOperation<IntStream, OptionalDouble> acquireAverage();

    TerminalOperation<IntStream, IntSummaryStatistics> acquireSummaryStatistics();


    TerminalOperation<IntStream, Boolean> createAnyMatch(IntPredicate predicate);

    TerminalOperation<IntStream, Boolean> createAllMatch(IntPredicate predicate);

    TerminalOperation<IntStream, Boolean> createNoneMatch(IntPredicate predicate);

    TerminalOperation<IntStream, OptionalInt> acquireFindFirst();

    TerminalOperation<IntStream, OptionalInt> acquireFindAny();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<IntStream, PrimitiveIterator.OfInt> acquireIterator();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<IntStream, Spliterator.OfInt> acquireSpliterator();
}