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
package com.speedment.jpastreamer.pipeline.terminal;

import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

public interface LongTerminalOperationFactory extends BaseStreamTerminalOperationFactory {

    TerminalOperation<LongStream, Void> createForEach(LongConsumer action);

    TerminalOperation<LongStream, Void> createForEachOrdered(LongConsumer action);

    TerminalOperation<LongStream, long[]> acquireToArray();

    TerminalOperation<LongStream, Long> createReduce(long identity, LongBinaryOperator op);

    TerminalOperation<LongStream, OptionalLong> createReduce(LongBinaryOperator op);

    <R> TerminalOperation<LongStream, R> createCollect(Supplier<R> supplier,
                                                       ObjLongConsumer<R> accumulator,
                                                       BiConsumer<R, R> combiner);

    TerminalOperation<LongStream, Long> acquireSum();

    TerminalOperation<LongStream, OptionalLong> acquireMin();

    TerminalOperation<LongStream, OptionalLong> acquireMax();

    TerminalOperation<LongStream, Long> acquireCount();


    TerminalOperation<LongStream, OptionalDouble> acquireAverage();

    TerminalOperation<LongStream, LongSummaryStatistics> acquireSummaryStatistics();


    TerminalOperation<LongStream, Boolean> createAnyMatch(LongPredicate predicate);

    TerminalOperation<LongStream, Boolean> createAllMatch(LongPredicate predicate);

    TerminalOperation<LongStream, Boolean> createNoneMatch(LongPredicate predicate);

    TerminalOperation<LongStream, OptionalLong> acquireFindFirst();

    TerminalOperation<LongStream, OptionalLong> acquireFindAny();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<LongStream, PrimitiveIterator.OfLong> acquireIterator();

    @SuppressWarnings("unchecked")
    @Override
    TerminalOperation<LongStream, Spliterator.OfLong> acquireSpliterator();

}
