package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface IntIntermediateOperationFactory {

    IntermediateOperation<IntStream, IntStream> createFilter(IntPredicate predicate);

    IntermediateOperation<IntStream, IntStream> createMap(IntUnaryOperator mapper);

    <U> IntermediateOperation<IntStream, Stream<U>> createMapToObj(IntFunction<? extends U> mapper);

    IntermediateOperation<IntStream, LongStream> createMapToLong(IntToLongFunction mapper);

    IntermediateOperation<IntStream, DoubleStream> createMapToDouble(IntToDoubleFunction mapper);


    IntermediateOperation<IntStream, IntStream> createFlatMap(IntFunction<? extends IntStream> mapper);


    IntermediateOperation<IntStream, IntStream> createDistinct();


    IntermediateOperation<IntStream, IntStream> createSorted();

    IntermediateOperation<IntStream, IntStream> createPeek(IntConsumer IntermediateOperator);


    IntermediateOperation<IntStream, IntStream> createLimit(long maxSize);

    IntermediateOperation<IntStream, IntStream> createSkip(long n);


    IntermediateOperation<IntStream, IntStream> createTakeWhile(IntPredicate predicate);

    IntermediateOperation<IntStream, IntStream> createDropWhile(IntPredicate predicate);

}