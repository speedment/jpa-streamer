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
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.intermediate.IntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.IntTerminalOperationFactory;

import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

final class IntStreamBuilder<E>
        extends BaseStreamBuilder<E, Integer, IntStream>
        implements IntStream {

    IntStreamBuilder(final BaseBuilderState<E> baseState) {
        super(baseState);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        add(iof().createFilter(predicate));
        return this;
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        add(iof().createMap(mapper));
        return this;
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        add(iof().createMapToObj(mapper));
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        add(iof().createMapToLong(mapper));
        linked();
        return new LongStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        add(iof().createMapToDouble(mapper));
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        add(iof().createFlatMap(mapper));
        return this;
    }

    @Override
    public IntStream distinct() {
        add(iof().acquireDistinct());
        return this;
    }

    @Override
    public IntStream sorted() {
        add(iof().acquireSorted());
        return this;
    }

    @Override
    public IntStream peek(IntConsumer action) {
        add(iof().createPeek(action));
        return this;
    }

    @Override
    public IntStream limit(long maxSize) {
        add(iof().createLimit(maxSize));
        return this;
    }

    @Override
    public IntStream skip(long n) {
        add(iof().createSkip(n));
        return this;
    }

    @Override
    public IntStream takeWhile(IntPredicate predicate) {
        add(iof().createTakeWhile(predicate));
        return this;
    }

    @Override
    public IntStream dropWhile(IntPredicate predicate) {
        add(iof().createDropWhile(predicate));
        return this;
    }

    @Override
    public Stream<Integer> boxed() {
        add(iof().acquireBoxed());
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public LongStream asLongStream() {
        add(iof().acquireAsLongStream());
        linked();
        return new LongStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream asDoubleStream() {
        add(iof().acquireAsDoubleStream());
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @Override
    public void forEach(IntConsumer action) {
        set(tof().createForEach(action));
        renderAndThenAccept();
    }

    @Override
    public void forEachOrdered(IntConsumer action) {
        set(tof().createForEachOrdered(action));
        renderAndThenAccept();
    }

    @Override
    public int[] toArray() {
        set(tof().acquireToArray());
        return renderAndThenApply();
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        set(tof().createReduce(identity, op));
        return renderAndThenApplyAsInt();
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        set(tof().createReduce(op));
        return renderAndThenApply();
    }

    @Override
    public <R> R collect(Supplier<R> supplier,
                         ObjIntConsumer<R> accumulator,
                         BiConsumer<R, R> combiner) {
        set(tof().createCollect(supplier, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public int sum() {
        set(tof().acquireSum());
        return renderAndThenApplyAsInt();
    }

    @Override
    public OptionalInt min() {
        set(tof().acquireMin());
        return renderAndThenApply();
    }

    @Override
    public OptionalInt max() {
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
    public IntSummaryStatistics summaryStatistics() {
        set(tof().acquireSummaryStatistics());
        return renderAndThenApply();
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        set(tof().createAnyMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        set(tof().createAllMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        set(tof().createNoneMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public OptionalInt findFirst() {
        set(tof().acquireFindFirst());
        return renderAndThenApply();
    }

    @Override
    public OptionalInt findAny() {
        set(tof().acquireFindAny());
        return renderAndThenApply();
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        set(tof().acquireIterator());
        return renderAndThenApply();
    }

    @Override
    public Spliterator.OfInt spliterator() {
        set(tof().acquireSpliterator());
        return renderAndThenApply();
    }

    private IntIntermediateOperationFactory iof() {
        return baseState().factories().intIntermediate();
    }

    private IntTerminalOperationFactory tof() {
        return baseState().factories().intTerminal();
    }

}
