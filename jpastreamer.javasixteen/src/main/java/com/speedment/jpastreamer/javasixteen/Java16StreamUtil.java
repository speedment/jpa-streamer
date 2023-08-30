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
package com.speedment.jpastreamer.javasixteen;

import com.speedment.jpastreamer.javasixteen.internal.InternalJava16StreamUtil;

import java.util.List;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg, Julia Gustafsson 
 */
public final class Java16StreamUtil {

    private Java16StreamUtil() {}

    /**
     * Delegates a Stream::toList operation to the Java platforms
     * underlying default Stream implementation. If run under Java 15 or prior, this
     * method will throw an UnsupportedOperationException.
     *
     * @param <T> Element type in the Stream
     * @param stream to apply the toList operation to
     * @throws UnsupportedOperationException if run under Java 15 or prior
     * @return a List containing the stream elements
     */
    public static <T> List<T> toList(Stream<T> stream) {
        return InternalJava16StreamUtil.toList(stream);
    }

    /**
     * Delegates a Stream::mapMulti operation to the Java platforms
     * underlying default Stream implementation. If run under Java 15 or prior, this
     * method will throw an UnsupportedOperationException.
     *
     * @param <T> The element type in the source Stream
     * @param <R> The element type of the new Stream
     * @param stream to apply the mapMulti operation to
     * @param mapper a non-interfering, stateless function that generates replacement elements
     * @throws UnsupportedOperationException if run under Java 15 or prior
     * @return the new Stream
     */
    public static <T, R> Stream<R> mapMulti(Stream<T> stream, BiConsumer<? super T,? super Consumer<R>> mapper) {
        return InternalJava16StreamUtil.mapMulti(stream, mapper);
    }

    /**
     * Delegates a Stream::mapMultiToInt operation to the Java platforms
     * underlying default Stream implementation. If run under Java 15 or prior, this
     * method will throw an UnsupportedOperationException.
     *
     * @param <T> The element type in the source Stream
     * @param stream to apply the mapMultiToInt operation to
     * @param mapper a non-interfering, stateless function that generates replacement elements
     * @throws UnsupportedOperationException if run under Java 15 or prior
     * @return the new Stream
     */
    public static <T> IntStream mapMultiToInt(Stream<T> stream, BiConsumer<? super T,? super IntConsumer> mapper) {
        return InternalJava16StreamUtil.mapMultiToInt(stream, mapper);
    }

    /**
     * Delegates a Stream::mapMultiToDouble operation to the Java platforms
     * underlying default Stream implementation. If run under Java 15 or prior, this
     * method will throw an UnsupportedOperationException.
     *
     * @param <T> The element type in the source Stream
     * @param stream to apply the mapMultiToDouble operation to
     * @param mapper a non-interfering, stateless function that generates replacement elements
     * @throws UnsupportedOperationException if run under Java 15 or prior
     * @return the new Stream
     */
    public static <T> DoubleStream mapMultiToDouble(Stream<T> stream, BiConsumer<? super T,? super DoubleConsumer> mapper) {
        return InternalJava16StreamUtil.mapMultiToDouble(stream, mapper);
    }

    /**
     * Delegates a Stream::mapMultiToLong operation to the Java platforms
     * underlying default Stream implementation. If run under Java 15 or prior, this
     * method will throw an UnsupportedOperationException.
     *
     * @param <T> The element type in the source Stream
     * @param stream to apply the mapMultiToLong operation to
     * @param mapper a non-interfering, stateless function that generates replacement elements
     * @throws UnsupportedOperationException if run under Java 15 or prior
     * @return the new Stream
     */
    public static <T> LongStream mapMultiToLong(Stream<T> stream, BiConsumer<? super T,? super LongConsumer> mapper) {
        return InternalJava16StreamUtil.mapMultiToLong(stream, mapper);
    }


}
