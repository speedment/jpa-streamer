package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.action.Action;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface IntermediateOperationFactory {

    <T> Action<Stream<T>, Stream<T>> createFilter(Predicate<? super T> predicate);


    <T, R> Action<Stream<T>, Stream<T>> createMap(Function<? super T, ? extends R> mapper);

    <T> Action<Stream<T>, IntStream> createMapToInt(ToIntFunction<? super T> mapper);

    <T> Action<Stream<T>, LongStream> createMapToLong(ToLongFunction<? super T> mapper);

    <T> Action<Stream<T>, DoubleStream> createMapToDouble(ToDoubleFunction<? super T> mapper);


    <T, R> Action<Stream<T>, Stream<T>> createFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    <T> Action<Stream<T>, IntStream> createFlatMapToInt(Function<? super T, ? extends IntStream> mapper);

    <T> Action<Stream<T>, LongStream> createFlatMapToLong(Function<? super T, ? extends LongStream> mapper);

    <T> Action<Stream<T>, DoubleStream> createFlatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);


    <T> Action<Stream<T>, Stream<T>> createDistinct();


    <T> Action<Stream<T>, Stream<T>> createSorted();

    <T> Action<Stream<T>, Stream<T>> createSorted(Comparator<? super T> comparator);


    <T> Action<Stream<T>, Stream<T>> createPeek(Consumer<? super T> action);


    <T> Action<Stream<T>, Stream<T>> createLimit(long maxSize);

    <T> Action<Stream<T>, Stream<T>> createSkip(long n);


    <T> Action<Stream<T>, Stream<T>> createTakeWhile(Predicate<? super T> predicate);

    <T> Action<Stream<T>, Stream<T>> createDropWhile(Predicate<? super T> predicate);


}
