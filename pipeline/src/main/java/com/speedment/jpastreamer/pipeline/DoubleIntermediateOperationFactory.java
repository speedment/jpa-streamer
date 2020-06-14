package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.action.Action;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface DoubleIntermediateOperationFactory {

    Action<DoubleStream, DoubleStream> createFilter(DoublePredicate predicate);

    Action<DoubleStream, DoubleStream> createMap(DoubleUnaryOperator mapper);

    <U> Action<DoubleStream, Stream<U>> createMapToObj(DoubleFunction<? extends U> mapper);

    Action<DoubleStream, LongStream> createMapToLong(DoubleToLongFunction mapper);

    Action<DoubleStream, DoubleStream> createMapToInt(DoubleToIntFunction mapper);


    Action<DoubleStream, DoubleStream> createFlatMap(DoubleFunction<? extends DoubleStream> mapper);


    Action<DoubleStream, DoubleStream> createDistinct();


    Action<DoubleStream, DoubleStream> createSorted();

    Action<DoubleStream, DoubleStream> createPeek(DoubleConsumer action);


    Action<DoubleStream, DoubleStream> createLimit(long maxSize);

    Action<DoubleStream, DoubleStream> createSkip(long n);


    Action<DoubleStream, DoubleStream> createTakeWhile(DoublePredicate predicate);

    Action<DoubleStream, DoubleStream> createDropWhile(DoublePredicate predicate);

}