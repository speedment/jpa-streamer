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

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

/**
 * A StreamBuilder that will accumulate intermediate and terminal operations
 * and, upon a terminal operation, will invoke a renderer to actually create a
 * Stream.
 *
 * @param <E> Entity type which is the same as T for the initial stream but
 *            might be different after map operations.
 * @param <T> Type of the Stream to create
 *
 */
final class StreamBuilder<E, T>
        extends BaseStreamBuilder<E, T, Stream<T>>
        implements Stream<T> {

    StreamBuilder(final BaseBuilderState<E> baseState) {
        super(baseState);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        add(iof().createFilter(predicate));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        add(iof().createMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        add(iof().createMapToInt(mapper));
        linked();
        return new IntStreamBuilder<>(baseState());
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        add(iof().createMapToLong(mapper));
        linked();
        return new LongStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        add(iof().createMapToDouble(mapper));
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        add(iof().createFlatMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        add(iof().createFlatMapToInt(mapper));
        linked();
        return new IntStreamBuilder<>(baseState());
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        add(iof().createFlatMapToLong(mapper));
        linked();
        return new LongStreamBuilder<>(baseState());
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        add(iof().createFlatMapToDouble(mapper));
        linked();
        return new DoubleStreamBuilder<>(baseState());
    }

    @Override
    public Stream<T> distinct() {
        add(iof().acquireDistinct());
        return this;
    }

    @Override
    public Stream<T> sorted() {
        add(iof().acquireSorted());
        return this;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        add(iof().createSorted(comparator));
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        add(iof().createPeek(action));
        return this;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        add(iof().createLimit(maxSize));
        return this;
    }

    @Override
    public Stream<T> skip(long n) {
        add(iof().createSkip(n));
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        set(tof().createForEach(action));
        renderAndThenAccept();
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        set(tof().createForEachOrdered(action));
        renderAndThenAccept();
    }

    @Override
    public Object[] toArray() {
        set(tof().acquireToArray());
        return renderAndThenApply();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        set(tof().createToArray(generator));
        return renderAndThenApply();
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        set(tof().createReduce(identity, accumulator));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        set(tof().createReduce(accumulator));
        return renderAndThenApply();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        set(tof().createReduce(identity, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        set(tof().createCollect(supplier, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        set(tof().createCollect(collector));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        set(tof().createMin(comparator));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        set(tof().createMax(comparator));
        return renderAndThenApply();
    }

    @Override
    public long count() {
        set(tof().acquireCount());
        return renderCount();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        set(tof().createAnyMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        set(tof().createAllMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        set(tof().createNoneMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public Optional<T> findFirst() {
        set(tof().acquireFindFirst());
        return renderAndThenApply();
    }

    @Override
    public Optional<T> findAny() {
        set(tof().acquireFindAny());
        return renderAndThenApply();
    }

    @Override
    public Iterator<T> iterator() {
        set(tof().acquireIterator());
        return renderAndThenApply();
    }

    @Override
    public Spliterator<T> spliterator() {
        set(tof().acquireSpliterator());
        return renderAndThenApply();
    }

    private IntermediateOperationFactory iof() {
        return baseState().factories().intermediate();
    }

    private TerminalOperationFactory tof() {
        return baseState().factories().terminal();
    }

}
