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
package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.LongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

import static java.util.Objects.requireNonNull;

public class InternalLongTerminalOperationFactory implements LongTerminalOperationFactory {

    private static final TerminalOperation<LongStream, long[]> TO_ARRAY = new ApplyTerminalOperation<>(
            TerminalOperationType.TO_ARRAY,
            LongStream.class,
            long[].class,
            LongStream::toArray);

    private static final TerminalOperation<LongStream, Long> SUM = new ApplyAsLongTerminalOperation<>(
            TerminalOperationType.SUM_LONG,
            LongStream.class,
            long.class,
            LongStream::sum);

    private static final TerminalOperation<LongStream, OptionalLong> MIN = new ApplyTerminalOperation<>(
            TerminalOperationType.MIN,
            LongStream.class,
            OptionalLong.class,
            LongStream::min);

    private static final TerminalOperation<LongStream, OptionalLong> MAX = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            LongStream.class,
            OptionalLong.class,
            LongStream::max);

    private static final TerminalOperation<LongStream, OptionalDouble> AVERAGE = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            LongStream.class,
            OptionalDouble.class,
            LongStream::average);

    private static final TerminalOperation<LongStream, LongSummaryStatistics> SUMMARY_STATISTICS = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            LongStream.class,
            LongSummaryStatistics.class,
            LongStream::summaryStatistics);

    private static final TerminalOperation<LongStream, OptionalLong> FIND_FIRST = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_FIRST,
            LongStream.class,
            OptionalLong.class,
            LongStream::findFirst);

    private static final TerminalOperation<LongStream, OptionalLong> FIND_ANY = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_ANY,
            LongStream.class,
            OptionalLong.class,
            LongStream::findAny);

    private static final TerminalOperation<LongStream, Long> COUNT = new ApplyAsLongTerminalOperation<>(
            TerminalOperationType.COUNT,
            LongStream.class,
            long.class,
            LongStream::count);

    private static final TerminalOperation<LongStream, PrimitiveIterator.OfLong> ITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.ITERATOR,
            LongStream.class,
            PrimitiveIterator.OfLong.class,
            LongStream::iterator);

    private static final TerminalOperation<LongStream, Spliterator.OfLong> SPLITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.SPLITERATOR,
            LongStream.class,
            Spliterator.OfLong.class,
            LongStream::spliterator);

    @Override
    public TerminalOperation<LongStream, Void> createForEach(LongConsumer action) {
        requireNonNull(action);

        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH,
                LongStream.class,
                void.class,
                stream -> stream.forEach(action),
                action);
    }

    @Override
    public TerminalOperation<LongStream, Void> createForEachOrdered(LongConsumer action) {
        requireNonNull(action);
        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH_ORDERED,
                LongStream.class,
                void.class,
                stream -> stream.forEachOrdered(action),
                action);
    }

    @Override
    public TerminalOperation<LongStream, long[]> acquireToArray() {
        return TO_ARRAY;
    }

    @Override
    public TerminalOperation<LongStream, Long> createReduce(long identity, LongBinaryOperator op) {
        requireNonNull(op);
        return new ApplyAsLongTerminalOperation<>(
                TerminalOperationType.REDUCE,
                LongStream.class,
                long.class,
                stream -> stream.reduce(identity, op),
                identity, op);
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> createReduce(LongBinaryOperator op) {
        requireNonNull(op);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                LongStream.class,
                OptionalLong.class,
                stream -> stream.reduce(op),
                op);
    }

    @Override
    public <R> TerminalOperation<LongStream, R> createCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.COLLECT,
                LongStream.class,
                Object.class,
                stream -> stream.collect(supplier, accumulator, combiner),
                supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<LongStream, Long> acquireSum() {
        return SUM;
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireMin() {
        return MIN;
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireMax() {
        return MAX;
    }

    @Override
    public TerminalOperation<LongStream, Long> acquireCount() {
        return COUNT;
    }

    @Override
    public TerminalOperation<LongStream, OptionalDouble> acquireAverage() {
        return AVERAGE;
    }

    @Override
    public TerminalOperation<LongStream, LongSummaryStatistics> acquireSummaryStatistics() {
        return SUMMARY_STATISTICS;
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createAnyMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ANY_MATCH,
                LongStream.class,
                boolean.class,
                stream -> stream.anyMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createAllMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ALL_MATCH,
                LongStream.class,
                boolean.class,
                stream -> stream.allMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<LongStream, Boolean> createNoneMatch(LongPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.NONE_MATCH,
                LongStream.class,
                boolean.class,
                stream -> stream.noneMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireFindFirst() {
        return FIND_FIRST;
    }

    @Override
    public TerminalOperation<LongStream, OptionalLong> acquireFindAny() {
        return FIND_ANY;
    }

    @Override
    public TerminalOperation<LongStream, PrimitiveIterator.OfLong> acquireIterator() {
        return ITERATOR;
    }

    @Override
    public TerminalOperation<LongStream, Spliterator.OfLong> acquireSpliterator() {
        return SPLITERATOR;
    }
}
