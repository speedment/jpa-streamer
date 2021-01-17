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
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.javanine.Java9LongStreamAdditions;
import com.speedment.jpastreamer.pipeline.intermediate.LongIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.LongTerminalOperationFactory;

import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

final class LongStreamBuilder<E>
        extends BaseStreamBuilder<E, Long, LongStream>
        implements LongStream, Java9LongStreamAdditions {

    LongStreamBuilder(final BaseBuilderState<E> baseState) {
        super(baseState);
    }

    @Override
    public LongStream filter(LongPredicate predicate) {
        add(iof().createFilter(predicate));
        return this;
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        add(iof().createMap(mapper));
        return this;
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        add(iof().createMapToObj(mapper));
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        add(iof().createMapToInt(mapper));
        linked();
        return new IntStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        add(iof().createMapToDouble(mapper));
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        add(iof().createFlatMap(mapper));
        return this;
    }

    @Override
    public LongStream distinct() {
        add(iof().acquireDistinct());
        return this;
    }

    @Override
    public LongStream sorted() {
        add(iof().acquireSorted());
        return this;
    }

    @Override
    public LongStream peek(LongConsumer action) {
        add(iof().createPeek(action));
        return this;
    }

    @Override
    public LongStream limit(long maxSize) {
        add(iof().createLimit(maxSize));
        return this;
    }

    @Override
    public LongStream skip(long n) {
        add(iof().createSkip(n));
        return this;
    }

    @Override
    public LongStream takeWhile(LongPredicate predicate) {
        add(iof().createTakeWhile(predicate));
        return this;
    }

    @Override
    public LongStream dropWhile(LongPredicate predicate) {
        add(iof().createDropWhile(predicate));
        return this;
    }

    @Override
    public Stream<Long> boxed() {
        add(iof().acquireBoxed());
        linked();
        return new StreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream asDoubleStream() {
        add(iof().acquireAsDoubleStream());
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @Override
    public void forEach(LongConsumer action) {
        set(tof().createForEach(action));
        renderAndThenAccept();
    }

    @Override
    public void forEachOrdered(LongConsumer action) {
        set(tof().createForEachOrdered(action));
        renderAndThenAccept();
    }

    @Override
    public long[] toArray() {
        set(tof().acquireToArray());
        return renderAndThenApply();
    }

    @Override
    public long reduce(long identity, LongBinaryOperator op) {
        set(tof().createReduce(identity, op));
        return renderAndThenApplyAsLong();
    }

    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        set(tof().createReduce(op));
        return renderAndThenApply();
    }

    @Override
    public <R> R collect(Supplier<R> supplier,
                         ObjLongConsumer<R> accumulator,
                         BiConsumer<R, R> combiner) {
        set(tof().createCollect(supplier, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public long sum() {
        set(tof().acquireSum());
        return renderAndThenApplyAsLong();
    }

    @Override
    public OptionalLong min() {
        set(tof().acquireMin());
        return renderAndThenApply();
    }

    @Override
    public OptionalLong max() {
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
    public LongSummaryStatistics summaryStatistics() {
        set(tof().acquireSummaryStatistics());
        return renderAndThenApply();
    }

    @Override
    public boolean anyMatch(LongPredicate predicate) {
        set(tof().createAnyMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean allMatch(LongPredicate predicate) {
        set(tof().createAllMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean noneMatch(LongPredicate predicate) {
        set(tof().createNoneMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public OptionalLong findFirst() {
        set(tof().acquireFindFirst());
        return renderAndThenApply();
    }

    @Override
    public OptionalLong findAny() {
        set(tof().acquireFindAny());
        return renderAndThenApply();
    }

    @Override
    public PrimitiveIterator.OfLong iterator() {
        set(tof().acquireIterator());
        return renderAndThenApply();
    }

    @Override
    public Spliterator.OfLong spliterator() {
        set(tof().acquireSpliterator());
        return renderAndThenApply();
    }

    private LongIntermediateOperationFactory iof() {
        return baseState().factories().longIntermediate();
    }

    private LongTerminalOperationFactory tof() {
        return baseState().factories().longTerminal();
    }

}