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
package com.speedment.jpastreamer.javasixteen.internal;

import com.speedment.jpastreamer.exception.JPAStreamerException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg, Julia Gustafsson 
 */
public final class InternalJava16StreamUtil {

    private InternalJava16StreamUtil() {}

    private static final String TO_LIST = "toList";
    private static final String MAP_MULTI = "mapMulti";
    private static final String MAP_MULTI_TO_INT = "mapMultiToInt";
    private static final String MAP_MULTI_TO_LONG = "mapMultiToLong";
    private static final String MAP_MULTI_TO_DOUBLE = "mapMultiToDouble";

    // mapMulti() 
    private static final MethodType MAP_MULTI_DOUBLE_METHOD_TYPE =
            MethodType.methodType(DoubleStream.class, BiConsumer.class);
    static final MethodHandle MAP_MULTI_DOUBLE_HANDLE =
            createMethodHandle(MAP_MULTI_TO_DOUBLE, Stream.class, MAP_MULTI_DOUBLE_METHOD_TYPE);

    private static final MethodType MAP_MULTI_LONG_METHOD_TYPE =
            MethodType.methodType(LongStream.class, BiConsumer.class);
    static final MethodHandle MAP_MULTI_LONG_HANDLE =
            createMethodHandle(MAP_MULTI_TO_LONG, Stream.class, MAP_MULTI_LONG_METHOD_TYPE);

    private static final MethodType MAP_MULTI_INT_METHOD_TYPE =
            MethodType.methodType(IntStream.class, BiConsumer.class);
    static final MethodHandle MAP_MULTI_INT_HANDLE =
            createMethodHandle(MAP_MULTI_TO_INT, Stream.class, MAP_MULTI_INT_METHOD_TYPE);

    private static final MethodType MAP_MULTI_METHOD_TYPE =
            MethodType.methodType(Stream.class, BiConsumer.class);
    private static final MethodHandle MAP_MULTI_METHOD_HANDLE =
            createMethodHandle(MAP_MULTI, Stream.class, MAP_MULTI_METHOD_TYPE);

    // toList() 
    private static final MethodType TO_LIST_METHOD_TYPE = 
            MethodType.methodType(List.class);
    private static final MethodHandle TO_LIST_METHOD_HANDLE =
            createMethodHandle(TO_LIST, Stream.class, TO_LIST_METHOD_TYPE);
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Stream<T> stream) {
        requireNonNull(stream);
        if (TO_LIST_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(TO_LIST);
        }
        try {
            final Object obj = TO_LIST_METHOD_HANDLE.invoke(stream);
            return (List<T>) obj;
        } catch (Throwable t) {
            throw new JPAStreamerException(t);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T, R> Stream<R> mapMulti(Stream<T> stream, BiConsumer<? super T,? super Consumer<R>> mapper) {
        requireNonNull(stream);
        requireNonNull(mapper);
        if (MAP_MULTI_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(MAP_MULTI);
        }
        try {
            final Object obj = MAP_MULTI_METHOD_HANDLE.invoke(stream, mapper);
            return (Stream<R>) obj;
        } catch (Throwable t) {
            throw new JPAStreamerException(t);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> IntStream mapMultiToInt(Stream<T> stream, BiConsumer<? super T, ? super IntConsumer> mapper) {
        requireNonNull(stream);
        requireNonNull(mapper);
        if (MAP_MULTI_INT_HANDLE == null) {
            throw newUnsupportedOperationException(MAP_MULTI);
        }
        try {
            final Object obj = MAP_MULTI_INT_HANDLE.invoke(stream, mapper);
            return (IntStream) obj;
        } catch (Throwable t) {
            throw new JPAStreamerException(t);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> DoubleStream mapMultiToDouble(Stream<T> stream, BiConsumer<? super T,? super DoubleConsumer> mapper) {
        requireNonNull(stream);
        requireNonNull(mapper);
        if (MAP_MULTI_DOUBLE_HANDLE == null) {
            throw newUnsupportedOperationException(MAP_MULTI);
        }
        try {
            final Object obj = MAP_MULTI_DOUBLE_HANDLE.invoke(stream, mapper);
            return (DoubleStream) obj;
        } catch (Throwable t) {
            throw new JPAStreamerException(t);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> LongStream mapMultiToLong(Stream<T> stream, BiConsumer<? super T,? super LongConsumer> mapper) {
        requireNonNull(stream);
        requireNonNull(mapper);
        if (MAP_MULTI_LONG_HANDLE == null) {
            throw newUnsupportedOperationException(MAP_MULTI);
        }
        try {
            final Object obj = MAP_MULTI_LONG_HANDLE.invoke(stream, mapper);
            return (LongStream) obj;
        } catch (Throwable t) {
            throw new JPAStreamerException(t);
        }
    }

    private static MethodHandle createMethodHandle(String methodName, Class<?> refc, MethodType methodType) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            return lookup.findVirtual(refc, methodName, methodType);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            // We are running under Java 15 or prior
            return null;
        }
    }

    private static UnsupportedOperationException newUnsupportedOperationException(String methodName) {
        return new UnsupportedOperationException("Stream::" + methodName + " is not supported by this Java version. Use Java 16 or greater.");
    }

}
