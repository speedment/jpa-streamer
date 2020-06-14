package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.action.Action;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface LongIntermediateOperationFactory {

    Action<LongStream, LongStream> createFilter(LongPredicate predicate);

    Action<LongStream, LongStream> createMap(LongUnaryOperator mapper);

    <U> Action<LongStream, Stream<U>> createMapToObj(LongFunction<? extends U> mapper);

    Action<LongStream, LongStream> createMapToInt(LongToIntFunction mapper);

    Action<LongStream, DoubleStream> createMapToDouble(LongToDoubleFunction mapper);


    Action<LongStream, LongStream> createFlatMap(LongFunction<? extends LongStream> mapper);


    Action<LongStream, LongStream> createDistinct();


    Action<LongStream, LongStream> createSorted();

    Action<LongStream, LongStream> createPeek(LongConsumer action);


    Action<LongStream, LongStream> createLimit(long maxSize);

    Action<LongStream, LongStream> createSkip(long n);


    Action<LongStream, LongStream> createTakeWhile(LongPredicate predicate);

    Action<LongStream, LongStream> createDropWhile(LongPredicate predicate);

}