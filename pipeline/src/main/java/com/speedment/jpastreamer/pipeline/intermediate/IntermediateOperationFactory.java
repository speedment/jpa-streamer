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

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface IntermediateOperationFactory {

    <T> IntermediateOperation<Stream<T>, Stream<T>> createFilter(Predicate<? super T> predicate);


    <T, R> IntermediateOperation<Stream<T>, Stream<R>> createMap(Function<? super T, ? extends R> mapper);

    <T> IntermediateOperation<Stream<T>, IntStream> createMapToInt(ToIntFunction<? super T> mapper);

    <T> IntermediateOperation<Stream<T>, LongStream> createMapToLong(ToLongFunction<? super T> mapper);

    <T> IntermediateOperation<Stream<T>, DoubleStream> createMapToDouble(ToDoubleFunction<? super T> mapper);


    <T, R> IntermediateOperation<Stream<T>, Stream<R>> createFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    <T> IntermediateOperation<Stream<T>, IntStream> createFlatMapToInt(Function<? super T, ? extends IntStream> mapper);

    <T> IntermediateOperation<Stream<T>, LongStream> createFlatMapToLong(Function<? super T, ? extends LongStream> mapper);

    <T> IntermediateOperation<Stream<T>, DoubleStream> createFlatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);


    <T> IntermediateOperation<Stream<T>, Stream<T>> acquireDistinct();


    <T> IntermediateOperation<Stream<T>, Stream<T>> acquireSorted();

    <T> IntermediateOperation<Stream<T>, Stream<T>> createSorted(Comparator<? super T> comparator);


    <T> IntermediateOperation<Stream<T>, Stream<T>> createPeek(Consumer<? super T> IntermediateOperator);


    <T> IntermediateOperation<Stream<T>, Stream<T>> createLimit(long maxSize);

    <T> IntermediateOperation<Stream<T>, Stream<T>> createSkip(long n);


    <T> IntermediateOperation<Stream<T>, Stream<T>> createTakeWhile(Predicate<? super T> predicate);

    <T> IntermediateOperation<Stream<T>, Stream<T>> createDropWhile(Predicate<? super T> predicate);

}