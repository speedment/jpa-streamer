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

import com.speedment.jpastreamer.pipeline.intermediate.DoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalDoubleIntermediateOperationFactory;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardDoubleIntermediateOperationFactory implements DoubleIntermediateOperationFactory {

    private final DoubleIntermediateOperationFactory delegate = new InternalDoubleIntermediateOperationFactory();

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createFilter(DoublePredicate predicate) {
        return delegate.createFilter(predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createMap(DoubleUnaryOperator mapper) {
        return delegate.createMap(mapper);
    }

    @Override
    public <U> IntermediateOperation<DoubleStream, Stream<U>> createMapToObj(DoubleFunction<? extends U> mapper) {
        return delegate.createMapToObj(mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, LongStream> createMapToLong(DoubleToLongFunction mapper) {
        return delegate.createMapToLong(mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, IntStream> createMapToInt(DoubleToIntFunction mapper) {
        return delegate.createMapToInt(mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createFlatMap(DoubleFunction<? extends DoubleStream> mapper) {
        return delegate.createFlatMap(mapper);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> acquireDistinct() {
        return delegate.acquireDistinct();
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> acquireSorted() {
        return delegate.acquireSorted();
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createPeek(DoubleConsumer action) {
        return delegate.createPeek(action);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createLimit(long maxSize) {
        return delegate.createLimit(maxSize);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createSkip(long n) {
        return delegate.createSkip(n);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createTakeWhile(DoublePredicate predicate) {
        return delegate.createTakeWhile(predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, DoubleStream> createDropWhile(DoublePredicate predicate) {
        return delegate.createDropWhile(predicate);
    }

    @Override
    public IntermediateOperation<DoubleStream, Stream<Double>> acquireBoxed() {
        return delegate.acquireBoxed();
    }
}