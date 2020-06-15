package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperator;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface IntermediateOperationFactory {

    <T> IntermediateOperator<Stream<T>, Stream<T>> createFilter(Predicate<? super T> predicate);


    <T, R> IntermediateOperator<Stream<T>, Stream<R>> createMap(Function<? super T, ? extends R> mapper);

    <T> IntermediateOperator<Stream<T>, IntStream> createMapToInt(ToIntFunction<? super T> mapper);

    <T> IntermediateOperator<Stream<T>, LongStream> createMapToLong(ToLongFunction<? super T> mapper);

    <T> IntermediateOperator<Stream<T>, DoubleStream> createMapToDouble(ToDoubleFunction<? super T> mapper);


    <T, R> IntermediateOperator<Stream<T>, Stream<R>> createFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    <T> IntermediateOperator<Stream<T>, IntStream> createFlatMapToInt(Function<? super T, ? extends IntStream> mapper);

    <T> IntermediateOperator<Stream<T>, LongStream> createFlatMapToLong(Function<? super T, ? extends LongStream> mapper);

    <T> IntermediateOperator<Stream<T>, DoubleStream> createFlatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);


    <T> IntermediateOperator<Stream<T>, Stream<T>> createDistinct();


    <T> IntermediateOperator<Stream<T>, Stream<T>> createSorted();

    <T> IntermediateOperator<Stream<T>, Stream<T>> createSorted(Comparator<? super T> comparator);


    <T> IntermediateOperator<Stream<T>, Stream<T>> createPeek(Consumer<? super T> IntermediateOperator);


    <T> IntermediateOperator<Stream<T>, Stream<T>> createLimit(long maxSize);

    <T> IntermediateOperator<Stream<T>, Stream<T>> createSkip(long n);


    <T> IntermediateOperator<Stream<T>, Stream<T>> createTakeWhile(Predicate<? super T> predicate);

    <T> IntermediateOperator<Stream<T>, Stream<T>> createDropWhile(Predicate<? super T> predicate);


}