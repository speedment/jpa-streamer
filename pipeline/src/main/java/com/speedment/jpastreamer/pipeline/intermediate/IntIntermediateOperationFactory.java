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


    IntermediateOperation<IntStream, IntStream> acquireDistinct();

    IntermediateOperation<IntStream, IntStream> acquireSorted();

    IntermediateOperation<IntStream, IntStream> createPeek(IntConsumer action);


    IntermediateOperation<IntStream, IntStream> createLimit(long maxSize);

    IntermediateOperation<IntStream, IntStream> createSkip(long n);


    IntermediateOperation<IntStream, IntStream> createTakeWhile(IntPredicate predicate);

    IntermediateOperation<IntStream, IntStream> createDropWhile(IntPredicate predicate);

}