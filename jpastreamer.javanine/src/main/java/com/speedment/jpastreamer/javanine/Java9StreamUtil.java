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
package com.speedment.jpastreamer.javanine;

import com.speedment.jpastreamer.javanine.internal.InternalJava9StreamUtil;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class Java9StreamUtil {

    private Java9StreamUtil() {}

    /**
     * Delegates a DoubleStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    public static DoubleStream takeWhile(DoubleStream stream, DoublePredicate predicate) {
        return InternalJava9StreamUtil.takeWhile(stream, predicate);
    }

    /**
     * Delegates a DoubleStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    public static DoubleStream dropWhile(DoubleStream stream, DoublePredicate predicate) {
        return InternalJava9StreamUtil.dropWhile(stream, predicate);
    }

    /**
     * Delegates an IntStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    public static IntStream takeWhile(IntStream stream, IntPredicate predicate) {
        return InternalJava9StreamUtil.takeWhile(stream, predicate);
    }

    /**
     * Delegates an InteStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        return InternalJava9StreamUtil.takeWhile(stream, predicate);
    }

    /**
     * Delegates a LongStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    public static LongStream takeWhile(LongStream stream, LongPredicate predicate) {
        return InternalJava9StreamUtil.takeWhile(stream, predicate);
    }

    /**
     * Delegates a LongStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    public static LongStream dropWhile(LongStream stream, LongPredicate predicate) {
        return InternalJava9StreamUtil.dropWhile(stream, predicate);
    }

    /**
     * Delegates a Stream::takeWhile operation to the Java platforms underlying
     * default Stream implementation. If run under Java 8, this method will
     * throw an UnsupportedOperationException.
     *
     * @param <T> Element type in the Stream
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
        return InternalJava9StreamUtil.takeWhile(stream, predicate);
    }

    /**
     * Delegates a Stream::dropWhile operation to the Java platforms underlying
     * default Stream implementation. If run under Java 8, this method will
     * throw an UnsupportedOperationException.
     *
     * @param <T> Element type in the Stream
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<? super T> predicate) {
        return InternalJava9StreamUtil.dropWhile(stream, predicate);
    }

}
