package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.*;

final class StreamBuilder<T> implements Stream<T> {

    private final Factories factories;
    private final Pipeline<T> pipeline;
    private final BaseStreamSupport support;

    StreamBuilder(final Factories factories,
                  final Class<T> root) {

        this.factories = requireNonNull(factories);
        this.pipeline = factories.pipeline().createPipeline(root);
        support = new BaseStreamSupport(pipeline);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        add(i().createFilter(predicate));
        return this;
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        add(i().createMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        add(i().createMapToInt(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        add(i().createMapToLong(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        add(i().createMapToDouble(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        add(i().createFlatMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        add(i().createFlatMapToInt(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        add(i().createFlatMapToLong(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        add(i().createFlatMapToDouble(mapper));
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<T> distinct() {
        add(i().createDistinct());
        return this;
    }

    @Override
    public Stream<T> sorted() {
        add(i().createSorted());
        return this;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        add(i().createSorted(comparator));
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        add(i().createPeek(action));
        return this;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        add(i().createLimit(maxSize));
        return this;
    }

    @Override
    public Stream<T> skip(long n) {
        add(i().createSkip(n));
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {

    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return null;
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return Optional.empty();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return null;
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return null;
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public Optional<T> findFirst() {
        return Optional.empty();
    }

    @Override
    public Optional<T> findAny() {
        return Optional.empty();
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    @Override
    public boolean isParallel() {
        return pipeline.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        pipeline.parallel();
        return this;
    }

    @Override
    public Stream<T> parallel() {
        pipeline.parallel();
        return this;
    }

    @Override
    public Stream<T> unordered() {
        pipeline.ordered(false);
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        pipeline.closeHandlers().add(closeHandler);
        return this;
    }

    @Override
    public void close() {
        // Todo: Make it Exception tolerant
        pipeline.closeHandlers().forEach(Runnable::run);
    }


    private IntermediateOperationFactory i() {
        return factories.intermediate();
    }

    private TerminalOperationFactory t() {
        return factories.terminal();
    }

    private void add(final IntermediateOperation<Stream<T>, ?> intermediateOperation) {
        pipeline.intermediateOperations().add(intermediateOperation);
    }

}
