/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.pipeline.intermediate;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface DoubleIntermediateOperationFactory {

    IntermediateOperation<DoubleStream, DoubleStream> createFilter(DoublePredicate predicate);

    IntermediateOperation<DoubleStream, DoubleStream> createMap(DoubleUnaryOperator mapper);

    <U> IntermediateOperation<DoubleStream, Stream<U>> createMapToObj(DoubleFunction<? extends U> mapper);

    IntermediateOperation<DoubleStream, LongStream> createMapToLong(DoubleToLongFunction mapper);

    IntermediateOperation<DoubleStream, DoubleStream> createMapToInt(DoubleToIntFunction mapper);


    IntermediateOperation<DoubleStream, DoubleStream> createFlatMap(DoubleFunction<? extends DoubleStream> mapper);


    IntermediateOperation<DoubleStream, DoubleStream> createDistinct();


    IntermediateOperation<DoubleStream, DoubleStream> createSorted();

    IntermediateOperation<DoubleStream, DoubleStream> createPeek(DoubleConsumer IntermediateOperator);


    IntermediateOperation<DoubleStream, DoubleStream> createLimit(long maxSize);

    IntermediateOperation<DoubleStream, DoubleStream> createSkip(long n);


    IntermediateOperation<DoubleStream, DoubleStream> createTakeWhile(DoublePredicate predicate);

    IntermediateOperation<DoubleStream, DoubleStream> createDropWhile(DoublePredicate predicate);

}