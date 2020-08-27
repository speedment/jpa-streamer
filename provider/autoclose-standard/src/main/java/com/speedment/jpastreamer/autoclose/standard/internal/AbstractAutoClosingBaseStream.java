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
package com.speedment.jpastreamer.autoclose.standard.internal;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author     Per Minborg
 */
abstract class AbstractAutoClosingBaseStream<T, S extends BaseStream<T, S>> implements AutoCloseable {

    private final S stream;
    private final boolean allowStreamIteratorAndSpliterator;
    private final AtomicBoolean closed;

    AbstractAutoClosingBaseStream(
        final S stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        this.stream = requireNonNull(stream);
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
        this.closed = new AtomicBoolean();
    }

    protected S stream(){ return stream; }

    @Override
    public void close() {
        if (closed.compareAndSet(false,true)) {
            stream().close();
        }
    }

    boolean isAllowStreamIteratorAndSpliterator() {
        return allowStreamIteratorAndSpliterator;
    }

    boolean finallyClose(BooleanSupplier bs) {
        try {
            return bs.getAsBoolean();
        } finally {
            close();
        }
    }

    long finallyClose(LongSupplier lp) {
        try {
            return lp.getAsLong();
        } finally {
            close();
        }
    }

    int finallyClose(IntSupplier is) {
        try {
            return is.getAsInt();
        } finally {
            close();
        }
    }

    double finallyClose(DoubleSupplier ds) {
        try {
            return ds.getAsDouble();
        } finally {
            close();
        }
    }

    void finallyClose(Runnable r) {
        try {
            r.run();
        } finally {
            close();
        }
    }

    <U> U finallyClose(Supplier<U> s) {
        try {
            return s.get();
        } finally {
            close();
        }
    }

    <U> Stream<U> wrap(Stream<U> stream) {
        return wrap(stream, AutoClosingStream::new);
    }

    IntStream wrap(IntStream stream) {
        return wrap(stream, AutoClosingIntStream::new);
    }

    LongStream wrap(LongStream stream) {
        return wrap(stream, AutoClosingLongStream::new);
    }

    DoubleStream wrap(DoubleStream stream) {
        return wrap(stream, AutoClosingDoubleStream::new);
    }

    private <U> U wrap(U stream, BiFunction<U, Boolean, U> wrapper) {
        if (stream instanceof AbstractAutoClosingBaseStream) {
            return stream; // If we already are wrapped, then do not wrap again
        }
        return wrapper.apply(stream, allowStreamIteratorAndSpliterator);
    }

    static UnsupportedOperationException newUnsupportedException(String methodName) {
        return new UnsupportedOperationException("The " + methodName + "() method is unsupported because otherwise the AutoClose property cannot be guaranteed");
    }

}