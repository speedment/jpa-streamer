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
package com.speedment.jpastreamer.pipeline.intermediate.standard;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.LongIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.standard.internal.InternalLongIntermediateOperationFactory;

import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardLongIntermediateOperationFactory implements LongIntermediateOperationFactory {

    private final LongIntermediateOperationFactory delegate = new InternalLongIntermediateOperationFactory();

    @Override
    public IntermediateOperation<LongStream, LongStream> createFilter(LongPredicate predicate) {
        return delegate.createFilter(predicate);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createMap(LongUnaryOperator mapper) {
        return delegate.createMap(mapper);
    }

    @Override
    public <U> IntermediateOperation<LongStream, Stream<U>> createMapToObj(LongFunction<? extends U> mapper) {
        return delegate.createMapToObj(mapper);
    }

    @Override
    public IntermediateOperation<LongStream, IntStream> createMapToInt(LongToIntFunction mapper) {
        return delegate.createMapToInt(mapper);
    }

    @Override
    public IntermediateOperation<LongStream, DoubleStream> createMapToDouble(LongToDoubleFunction mapper) {
        return delegate.createMapToDouble(mapper);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createFlatMap(LongFunction<? extends LongStream> mapper) {
        return delegate.createFlatMap(mapper);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> acquireDistinct() {
        return delegate.acquireDistinct();
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> acquireSorted() {
        return delegate.acquireSorted();
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createPeek(LongConsumer action) {
        return delegate.createPeek(action);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createLimit(long maxSize) {
        return delegate.createLimit(maxSize);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createSkip(long n) {
        return delegate.createSkip(n);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createTakeWhile(LongPredicate predicate) {
        return delegate.createTakeWhile(predicate);
    }

    @Override
    public IntermediateOperation<LongStream, LongStream> createDropWhile(LongPredicate predicate) {
        return delegate.createDropWhile(predicate);
    }

    @Override
    public IntermediateOperation<LongStream, Stream<Long>> acquireBoxed() {
        return delegate.acquireBoxed();
    }

    @Override
    public IntermediateOperation<LongStream, DoubleStream> acquireAsDoubleStream() {
        return delegate.acquireAsDoubleStream();
    }
}
