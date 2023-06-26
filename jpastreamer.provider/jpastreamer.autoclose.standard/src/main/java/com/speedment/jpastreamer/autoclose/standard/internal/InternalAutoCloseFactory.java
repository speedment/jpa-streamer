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
package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class InternalAutoCloseFactory implements AutoCloseFactory {

    @Override
    public <T> Stream<T> createAutoCloseStream(final Stream<T> stream) {
        return new AutoClosingStream<>(stream);
    }

    @Override
    public IntStream createAutoCloseIntStream(final IntStream intStream) {
        return new AutoClosingIntStream(intStream);
    }

    @Override
    public LongStream createAutoCloseLongStream(final LongStream longStream) {
        return new AutoClosingLongStream(longStream);
    }

    @Override
    public DoubleStream createAutoCloseDoubleStream(final DoubleStream doubleStream) {
        return new AutoClosingDoubleStream(doubleStream);
    }
}
