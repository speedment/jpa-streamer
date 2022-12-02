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
package com.speedment.jpastreamer.pipeline.standard.intermediate;

import com.speedment.jpastreamer.pipeline.intermediate.IntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalIntIntermediateOperationFactory;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardIntIntermediateOperationFactory implements IntIntermediateOperationFactory {

    private final IntIntermediateOperationFactory delegate = new InternalIntIntermediateOperationFactory();

    @Override
    public IntermediateOperation<IntStream, IntStream> createFilter(IntPredicate predicate) {
        return delegate.createFilter(predicate);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createMap(IntUnaryOperator mapper) {
        return delegate.createMap(mapper);
    }

    @Override
    public <U> IntermediateOperation<IntStream, Stream<U>> createMapToObj(IntFunction<? extends U> mapper) {
        return delegate.createMapToObj(mapper);
    }

    @Override
    public IntermediateOperation<IntStream, LongStream> createMapToLong(IntToLongFunction mapper) {
        return delegate.createMapToLong(mapper);
    }

    @Override
    public IntermediateOperation<IntStream, DoubleStream> createMapToDouble(IntToDoubleFunction mapper) {
        return delegate.createMapToDouble(mapper);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createFlatMap(IntFunction<? extends IntStream> mapper) {
        return delegate.createFlatMap(mapper);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> acquireDistinct() {
        return delegate.acquireDistinct();
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> acquireSorted() {
        return delegate.acquireSorted();
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createPeek(IntConsumer action) {
        return delegate.createPeek(action);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createLimit(long maxSize) {
        return delegate.createLimit(maxSize);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createSkip(long n) {
        return delegate.createSkip(n);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createTakeWhile(IntPredicate predicate) {
        return delegate.createTakeWhile(predicate);
    }

    @Override
    public IntermediateOperation<IntStream, IntStream> createDropWhile(IntPredicate predicate) {
        return delegate.createDropWhile(predicate);
    }

    @Override
    public IntermediateOperation<IntStream, Stream<Integer>> acquireBoxed() {
        return delegate.acquireBoxed();
    }

    @Override
    public IntermediateOperation<IntStream, LongStream> acquireAsLongStream() {
        return delegate.acquireAsLongStream();
    }

    @Override
    public IntermediateOperation<IntStream, DoubleStream> acquireAsDoubleStream() {
        return delegate.acquireAsDoubleStream();
    }
}
