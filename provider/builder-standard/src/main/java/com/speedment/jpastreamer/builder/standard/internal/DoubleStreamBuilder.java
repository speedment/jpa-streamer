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
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.javanine.Java9DoubleStreamAdditions;
import com.speedment.jpastreamer.pipeline.intermediate.DoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.DoubleTerminalOperationFactory;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

final class DoubleStreamBuilder<E>
        extends BaseStreamBuilder<E, Double, DoubleStream>
        implements DoubleStream, Java9DoubleStreamAdditions {

    DoubleStreamBuilder(final BaseBuilderState<E> baseState) {
        super(baseState);
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        add(iof().createFilter(predicate));
        return this;
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        add(iof().createMap(mapper));
        return this;
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        add(iof().createMapToObj(mapper));
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        add(iof().createMapToLong(mapper));
        linked();
        return new LongStreamBuilder<>(baseState());
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        add(iof().createMapToInt(mapper));
        linked();
        return new IntStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        add(iof().createFlatMap(mapper));
        return this;
    }

    @Override
    public DoubleStream distinct() {
        add(iof().acquireDistinct());
        return this;
    }

    @Override
    public DoubleStream sorted() {
        add(iof().acquireSorted());
        return this;
    }

    @Override
    public DoubleStream peek(DoubleConsumer action) {
        add(iof().createPeek(action));
        return this;
    }

    @Override
    public DoubleStream limit(long maxSize) {
        add(iof().createLimit(maxSize));
        return this;
    }

    @Override
    public DoubleStream skip(long n) {
        add(iof().createSkip(n));
        return this;
    }

    @Override
    public DoubleStream takeWhile(DoublePredicate predicate) {
        add(iof().createTakeWhile(predicate));
        return this;
    }

    @Override
    public DoubleStream dropWhile(DoublePredicate predicate) {
        add(iof().createDropWhile(predicate));
        return this;
    }

    @Override
    public Stream<Double> boxed() {
        add(iof().acquireBoxed());
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public void forEach(DoubleConsumer action) {
        set(tof().createForEach(action));
        renderAndThenAccept();
    }

    @Override
    public void forEachOrdered(DoubleConsumer action) {
        set(tof().createForEachOrdered(action));
        renderAndThenAccept();
    }

    @Override
    public double[] toArray() {
        set(tof().acquireToArray());
        return renderAndThenApply();
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        set(tof().createReduce(identity, op));
        return renderAndThenApplyAsDouble();
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        set(tof().createReduce(op));
        return renderAndThenApply();
    }

    @Override
    public <R> R collect(Supplier<R> supplier,
                         ObjDoubleConsumer<R> accumulator,
                         BiConsumer<R, R> combiner) {
        set(tof().createCollect(supplier, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public double sum() {
        set(tof().acquireSum());
        return renderAndThenApplyAsDouble();
    }

    @Override
    public OptionalDouble min() {
        set(tof().acquireMin());
        return renderAndThenApply();
    }

    @Override
    public OptionalDouble max() {
        set(tof().acquireMax());
        return renderAndThenApply();
    }

    @Override
    public long count() {
        set(tof().acquireCount());
        return renderCount();
    }

    @Override
    public OptionalDouble average() {
        set(tof().acquireAverage());
        return renderAndThenApply();
    }

    @Override
    public DoubleSummaryStatistics summaryStatistics() {
        set(tof().acquireSummaryStatistics());
        return renderAndThenApply();
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        set(tof().createAnyMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        set(tof().createAllMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        set(tof().createNoneMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public OptionalDouble findFirst() {
        set(tof().acquireFindFirst());
        return renderAndThenApply();
    }

    @Override
    public OptionalDouble findAny() {
        set(tof().acquireFindAny());
        return renderAndThenApply();
    }

    @Override
    public PrimitiveIterator.OfDouble iterator() {
        set(tof().acquireIterator());
        return renderAndThenApply();
    }

    @Override
    public Spliterator.OfDouble spliterator() {
        set(tof().acquireSpliterator());
        return renderAndThenApply();
    }

    private DoubleIntermediateOperationFactory iof() {
        return baseState().factories().doubleIntermediate();
    }

    private DoubleTerminalOperationFactory tof() {
        return baseState().factories().doubleTerminal();
    }

}
