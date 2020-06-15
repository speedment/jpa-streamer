package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface LongIntermediateOperationFactory {

    IntermediateOperation<LongStream, LongStream> createFilter(LongPredicate predicate);

    IntermediateOperation<LongStream, LongStream> createMap(LongUnaryOperator mapper);

    <U> IntermediateOperation<LongStream, Stream<U>> createMapToObj(LongFunction<? extends U> mapper);

    IntermediateOperation<LongStream, LongStream> createMapToInt(LongToIntFunction mapper);

    IntermediateOperation<LongStream, DoubleStream> createMapToDouble(LongToDoubleFunction mapper);


    IntermediateOperation<LongStream, LongStream> createFlatMap(LongFunction<? extends LongStream> mapper);


    IntermediateOperation<LongStream, LongStream> createDistinct();


    IntermediateOperation<LongStream, LongStream> createSorted();

    IntermediateOperation<LongStream, LongStream> createPeek(LongConsumer IntermediateOperator);


    IntermediateOperation<LongStream, LongStream> createLimit(long maxSize);

    IntermediateOperation<LongStream, LongStream> createSkip(long n);


    IntermediateOperation<LongStream, LongStream> createTakeWhile(LongPredicate predicate);

    IntermediateOperation<LongStream, LongStream> createDropWhile(LongPredicate predicate);

}