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

import com.speedment.jpastreamer.pipeline.terminal.DoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;

import static java.util.Objects.requireNonNull;

public class InternalDoubleTerminalOperationFactory implements DoubleTerminalOperationFactory {

    private static final TerminalOperation<DoubleStream, double[]> TO_ARRAY = new ApplyTerminalOperation<>(
            TerminalOperationType.TO_ARRAY,
            DoubleStream.class,
            double[].class,
            DoubleStream::toArray);

    private static final TerminalOperation<DoubleStream, Double> SUM = new ApplyAsDoubleTerminalOperation<>(
            TerminalOperationType.SUM_DOUBLE,
            DoubleStream.class,
            double.class,
            DoubleStream::sum);

    private static final TerminalOperation<DoubleStream, OptionalDouble> MIN = new ApplyTerminalOperation<>(
            TerminalOperationType.MIN,
            DoubleStream.class,
            OptionalDouble.class,
            DoubleStream::min);

    private static final TerminalOperation<DoubleStream, OptionalDouble> MAX = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            DoubleStream.class,
            OptionalDouble.class,
            DoubleStream::max);

    private static final TerminalOperation<DoubleStream, OptionalDouble> AVERAGE = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            DoubleStream.class,
            OptionalDouble.class,
            DoubleStream::average);

    private static final TerminalOperation<DoubleStream, DoubleSummaryStatistics> SUMMARY_STATISTICS = new ApplyTerminalOperation<>(
            TerminalOperationType.MAX,
            DoubleStream.class,
            DoubleSummaryStatistics.class,
            DoubleStream::summaryStatistics);

    private static final TerminalOperation<DoubleStream, OptionalDouble> FIND_FIRST = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_FIRST,
            DoubleStream.class,
            OptionalDouble.class,
            DoubleStream::findFirst);

    private static final TerminalOperation<DoubleStream, OptionalDouble> FIND_ANY = new ApplyTerminalOperation<>(
            TerminalOperationType.FIND_ANY,
            DoubleStream.class,
            OptionalDouble.class,
            DoubleStream::findAny);

    private static final TerminalOperation<DoubleStream, Long> COUNT = new ApplyAsLongTerminalOperation<>(
            TerminalOperationType.COUNT,
            DoubleStream.class,
            long.class,
            DoubleStream::count);

    private static final TerminalOperation<DoubleStream, PrimitiveIterator.OfDouble> ITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.ITERATOR,
            DoubleStream.class,
            PrimitiveIterator.OfDouble.class,
            DoubleStream::iterator);

    private static final TerminalOperation<DoubleStream, Spliterator.OfDouble> SPLITERATOR = new ApplyTerminalOperation<>(
            TerminalOperationType.SPLITERATOR,
            DoubleStream.class,
            Spliterator.OfDouble.class,
            DoubleStream::spliterator);

    @Override
    public TerminalOperation<DoubleStream, Void> createForEach(DoubleConsumer action) {
        requireNonNull(action);

        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH,
                DoubleStream.class,
                void.class,
                stream -> stream.forEach(action),
                action);
    }

    @Override
    public TerminalOperation<DoubleStream, Void> createForEachOrdered(DoubleConsumer action) {
        requireNonNull(action);
        return new AcceptTerminalOperation<>(
                TerminalOperationType.FOR_EACH_ORDERED,
                DoubleStream.class,
                void.class,
                stream -> stream.forEachOrdered(action),
                action);
    }

    @Override
    public TerminalOperation<DoubleStream, double[]> acquireToArray() {
        return TO_ARRAY;
    }

    @Override
    public TerminalOperation<DoubleStream, Double> createReduce(double identity, DoubleBinaryOperator op) {
        requireNonNull(op);
        return new ApplyAsDoubleTerminalOperation<>(
                TerminalOperationType.REDUCE,
                DoubleStream.class,
                double.class,
                stream -> stream.reduce(identity, op),
                identity, op);
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> createReduce(DoubleBinaryOperator op) {
        requireNonNull(op);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.REDUCE,
                DoubleStream.class,
                OptionalDouble.class,
                stream -> stream.reduce(op),
                op);
    }

    @Override
    public <R> TerminalOperation<DoubleStream, R> createCollect(Supplier<R> supplier,
                                                                ObjDoubleConsumer<R> accumulator,
                                                                BiConsumer<R, R> combiner) {
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return new ApplyTerminalOperation<>(
                TerminalOperationType.COLLECT,
                DoubleStream.class,
                Object.class,
                stream -> stream.collect(supplier, accumulator, combiner),
                supplier, accumulator, combiner);
    }

    @Override
    public TerminalOperation<DoubleStream, Double> acquireSum() {
        return SUM;
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireMin() {
        return MIN;
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireMax() {
        return MAX;
    }

    @Override
    public TerminalOperation<DoubleStream, Long> acquireCount() {
        return COUNT;
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireAverage() {
        return AVERAGE;
    }

    @Override
    public TerminalOperation<DoubleStream, DoubleSummaryStatistics> acquireSummaryStatistics() {
        return SUMMARY_STATISTICS;
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createAnyMatch(DoublePredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ANY_MATCH,
                DoubleStream.class,
                boolean.class,
                stream -> stream.anyMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createAllMatch(DoublePredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.ALL_MATCH,
                DoubleStream.class,
                boolean.class,
                stream -> stream.allMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, Boolean> createNoneMatch(DoublePredicate predicate) {
        requireNonNull(predicate);
        return new TestTerminalOperation<>(
                TerminalOperationType.NONE_MATCH,
                DoubleStream.class,
                boolean.class,
                stream -> stream.noneMatch(predicate),
                predicate);
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireFindFirst() {
        return FIND_FIRST;
    }

    @Override
    public TerminalOperation<DoubleStream, OptionalDouble> acquireFindAny() {
        return FIND_ANY;
    }

    @Override
    public TerminalOperation<DoubleStream, PrimitiveIterator.OfDouble> acquireIterator() {
        return ITERATOR;
    }

    @Override
    public TerminalOperation<DoubleStream, Spliterator.OfDouble> acquireSpliterator() {
        return SPLITERATOR;
    }
}
