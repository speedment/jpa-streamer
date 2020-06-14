package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.action.Action;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface IntIntermediateOperationFactory {

    Action<IntStream, IntStream> createFilter(IntPredicate predicate);

    Action<IntStream, IntStream> createMap(IntUnaryOperator mapper);

    <U> Action<IntStream, Stream<U>> createMapToObj(IntFunction<? extends U> mapper);

    Action<IntStream, LongStream> createMapToLong(IntToLongFunction mapper);

    Action<IntStream, DoubleStream> createMapToDouble(IntToDoubleFunction mapper);


    Action<IntStream, IntStream> createFlatMap(IntFunction<? extends IntStream> mapper);


    Action<IntStream, IntStream> createDistinct();


    Action<IntStream, IntStream> createSorted();

    Action<IntStream, IntStream> createPeek(IntConsumer action);


    Action<IntStream, IntStream> createLimit(long maxSize);

    Action<IntStream, IntStream> createSkip(long n);


    Action<IntStream, IntStream> createTakeWhile(IntPredicate predicate);

    Action<IntStream, IntStream> createDropWhile(IntPredicate predicate);

}