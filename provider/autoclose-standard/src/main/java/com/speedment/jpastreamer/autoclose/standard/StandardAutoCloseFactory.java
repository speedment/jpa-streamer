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
package com.speedment.jpastreamer.autoclose.standard;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.autoclose.standard.internal.InternalAutoCloseFactory;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardAutoCloseFactory implements AutoCloseFactory {

    private final AutoCloseFactory delegate;

    public StandardAutoCloseFactory() {
        this.delegate = new InternalAutoCloseFactory();
    }

    @Override
    public <T> Stream<T> createAutoCloseStream(final Stream<T> stream) {
        return delegate.createAutoCloseStream(stream);
    }

    @Override
    public IntStream createAutoCloseIntStream(IntStream intStream) {
        return delegate.createAutoCloseIntStream(intStream);
    }

    @Override
    public LongStream createAutoCloseLongStream(LongStream longStream) {
        return delegate.createAutoCloseLongStream(longStream);
    }

    @Override
    public DoubleStream createAutoCloseDoubleStream(DoubleStream doubleStream) {
        return delegate.createAutoCloseDoubleStream(doubleStream);
    }
}