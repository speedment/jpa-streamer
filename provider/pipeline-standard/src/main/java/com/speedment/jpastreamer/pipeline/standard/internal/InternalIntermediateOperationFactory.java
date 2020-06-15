package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperator;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class InternalIntermediateOperationFactory implements IntermediateOperationFactory {

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createFilter(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        final UnaryOperator<Stream<T>> function = s -> s.filter(predicate);
        return new StandardIntermediateOperator<>(
                IntermediateOperationType.FILTER,
                Stream.class,
                Stream.class,
                function,
                predicate);

    }

    @Override
    public <T, R> IntermediateOperator<Stream<T>, Stream<T>> createMap(Function<? super T, ? extends R> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, IntStream> createMapToInt(ToIntFunction<? super T> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, LongStream> createMapToLong(ToLongFunction<? super T> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, DoubleStream> createMapToDouble(ToDoubleFunction<? super T> mapper) {
        return null;
    }

    @Override
    public <T, R> IntermediateOperator<Stream<T>, Stream<T>> createFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, IntStream> createFlatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, LongStream> createFlatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, DoubleStream> createFlatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createDistinct() {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createSorted() {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createSorted(Comparator<? super T> comparator) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createPeek(Consumer<? super T> IntermediateOperator) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createLimit(long maxSize) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createSkip(long n) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createTakeWhile(Predicate<? super T> predicate) {
        return null;
    }

    @Override
    public <T> IntermediateOperator<Stream<T>, Stream<T>> createDropWhile(Predicate<? super T> predicate) {
        return null;
    }
}
