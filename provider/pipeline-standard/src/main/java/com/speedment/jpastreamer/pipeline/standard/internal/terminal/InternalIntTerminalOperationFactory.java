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
package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.IntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

public class InternalIntTerminalOperationFactory implements IntTerminalOperationFactory {

    private static final TerminalOperation<IntStream, int[]> TO_ARRAY = new ApplyTerminalOperation<>(
            TerminalOperationType.TO_ARRAY,
            IntStream.class,
            int[].class,
            IntStream::toArray);

    private static final TerminalOperation<IntStream, Integer> SUM = new ApplyAsIntTerminalOperation<>(
            TerminalOperationType.SUM_INT,
            IntStream.class,
            int.class,
            IntStream::sum);

    private static final TerminalOperation<IntStream, OptionalInt> MIN = new ApplyTerminalOperation<>(
            TerminalOperationType.MIN,
            IntStream.class,
            OptionalInt.class,
            IntStream::min);

    private static final TerminalOperation<IntStream, OptionalInt> MAX = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            IntStream.class,
            OptionalInt.class,
            IntStream::max);

    private static final TerminalOperation<IntStream, OptionalDouble> AVERAGE = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            IntStream.class,
            OptionalDouble.class,
            IntStream::average);

    private static final TerminalOperation<IntStream, IntSummaryStatistics> SUMMARY_STATISTICS = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            IntStream.class,
            IntSummaryStatistics.class,
            IntStream::summaryStatistics);

    private static final TerminalOperation<IntStream, OptionalInt> FIND_FIRST = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_FIRST,
            IntStream.class,
            OptionalInt.class,
            IntStream::findFirst);

    private static final TerminalOperation<IntStream, OptionalInt> FIND_ANY = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_ANY,
            IntStream.class,
            OptionalInt.class,
            IntStream::findAny);

    private static final TerminalOperation<IntStream, Long> COUNT = new ApplyAsLongTerminalOperation<>(
            TerminalOperationType.COUNT,
            IntStream.class,
            long.class,
            IntStream::count);

    private static final TerminalOperation<IntStream, PrimitiveIterator.OfInt> ITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.ITERATOR,
            IntStream.class,
            PrimitiveIterator.OfInt.class,
            IntStream::iterator);

    private static final TerminalOperation<IntStream, Spliterator.OfInt> SPLITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.SPLITERATOR,
            IntStream.class,
            Spliterator.OfInt.class,
            IntStream::spliterator);

    @Override
    public TerminalOperation<IntStream, Void> createForEach(IntConsumer action) {
        requireNonNull(action);

        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH,
                IntStream.class,
                void.class,
                stream -> stream.forEach(action),
                action);
    }

    @Override
    public TerminalOperation<IntStream, Void> createForEachOrdered(IntConsumer action) {
        requireNonNull(action);
        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH_ORDERED,
                IntStream.class,
                void.class,
                stream -> stream.forEachOrdered(action),
                action);
    }

    @Override
    public TerminalOperation<IntStream, int[]> acquireToArray() {
        return TO_ARRAY;
    }

    @Override
    public TerminalOperation<IntStream, Integer> createReduce(int identity, IntBinaryOperator op) {
        requireNonNull(op);
        return new ApplyAsIntTerminalOperation<>(
                TerminalOperationType.REDUCE,
                IntStream.class,
                int.class,
                stream -> stream.reduce(identity, op),
                identity, op);
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> createReduce(IntBinaryOperator op) {
        requireNonNull(op);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                IntStream.class,
                OptionalInt.class,
                stream -> stream.reduce(op),
                op);
    }

    @Override
    public <R> TerminalOperation<IntStream, R> createCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.COLLECT,
                IntStream.class,
                Object.class,
                stream -> stream.collect(supplier, accumulator, combiner),
                supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<IntStream, Integer> acquireSum() {
        return SUM;
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireMin() {
        return MIN;
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireMax() {
        return MAX;
    }

    @Override
    public TerminalOperation<IntStream, Long> acquireCount() {
        return COUNT;
    }

    @Override
    public TerminalOperation<IntStream, OptionalDouble> acquireAverage() {
        return AVERAGE;
    }

    @Override
    public TerminalOperation<IntStream, IntSummaryStatistics> acquireSummaryStatistics() {
        return SUMMARY_STATISTICS;
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createAnyMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ANY_MATCH,
                IntStream.class,
                boolean.class,
                stream -> stream.anyMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createAllMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ALL_MATCH,
                IntStream.class,
                boolean.class,
                stream -> stream.allMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<IntStream, Boolean> createNoneMatch(IntPredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.NONE_MATCH,
                IntStream.class,
                boolean.class,
                stream -> stream.noneMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireFindFirst() {
        return FIND_FIRST;
    }

    @Override
    public TerminalOperation<IntStream, OptionalInt> acquireFindAny() {
        return FIND_ANY;
    }

    @Override
    public TerminalOperation<IntStream, PrimitiveIterator.OfInt> acquireIterator() {
        return ITERATOR;
    }

    @Override
    public TerminalOperation<IntStream, Spliterator.OfInt> acquireSpliterator() {
        return SPLITERATOR;
    }
}