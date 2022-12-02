/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.pipeline.standard.intermediate;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalIntermediateOperationFactory;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardIntermediateOperationFactory implements IntermediateOperationFactory {

    private final IntermediateOperationFactory delegate = new InternalIntermediateOperationFactory();

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createFilter(Predicate<? super T> predicate) {
        return delegate.createFilter(predicate);
    }

    @Override
    public <T, R> IntermediateOperation<Stream<T>, Stream<R>> createMap(Function<? super T, ? extends R> mapper) {
        return delegate.createMap(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, IntStream> createMapToInt(ToIntFunction<? super T> mapper) {
        return delegate.createMapToInt(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, LongStream> createMapToLong(ToLongFunction<? super T> mapper) {
        return delegate.createMapToLong(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, DoubleStream> createMapToDouble(ToDoubleFunction<? super T> mapper) {
        return delegate.createMapToDouble(mapper);
    }

    @Override
    public <T, R> IntermediateOperation<Stream<T>, Stream<R>> createFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return delegate.createFlatMap(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, IntStream> createFlatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return delegate.createFlatMapToInt(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, LongStream> createFlatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return delegate.createFlatMapToLong(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, DoubleStream> createFlatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return delegate.createFlatMapToDouble(mapper);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> acquireDistinct() {
        return delegate.acquireDistinct();
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> acquireSorted() {
        return delegate.acquireSorted();
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createSorted(Comparator<? super T> comparator) {
        return delegate.createSorted(comparator);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createPeek(Consumer<? super T> IntermediateOperator) {
        return delegate.createPeek(IntermediateOperator);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createLimit(long maxSize) {
        return delegate.createLimit(maxSize);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createSkip(long n) {
        return delegate.createSkip(n);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createTakeWhile(Predicate<? super T> predicate) {
        return delegate.createTakeWhile(predicate);
    }

    @Override
    public <T> IntermediateOperation<Stream<T>, Stream<T>> createDropWhile(Predicate<? super T> predicate) {
        return delegate.createDropWhile(predicate);
    }
}
