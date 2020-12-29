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

public interface LongIntermediateOperationFactory {

    IntermediateOperation<LongStream, LongStream> createFilter(LongPredicate predicate);

    IntermediateOperation<LongStream, LongStream> createMap(LongUnaryOperator mapper);

    <U> IntermediateOperation<LongStream, Stream<U>> createMapToObj(LongFunction<? extends U> mapper);

    IntermediateOperation<LongStream, LongStream> createMapToInt(LongToIntFunction mapper);

    IntermediateOperation<LongStream, DoubleStream> createMapToDouble(LongToDoubleFunction mapper);


    IntermediateOperation<LongStream, LongStream> createFlatMap(LongFunction<? extends LongStream> mapper);


    IntermediateOperation<LongStream, LongStream> acquireDistinct();


    IntermediateOperation<LongStream, LongStream> acquireSorted();

    IntermediateOperation<LongStream, LongStream> createPeek(LongConsumer IntermediateOperator);


    IntermediateOperation<LongStream, LongStream> createLimit(long maxSize);

    IntermediateOperation<LongStream, LongStream> createSkip(long n);


    IntermediateOperation<LongStream, LongStream> createTakeWhile(LongPredicate predicate);

    IntermediateOperation<LongStream, LongStream> createDropWhile(LongPredicate predicate);

}