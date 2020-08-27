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
package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

abstract class AbstractTerminalOperation<S extends BaseStream<?, S>, R> implements TerminalOperation<S, R> {

    private final TerminalOperationType type;
    private final Class<? super S> streamType;
    private final Class<? super R> returnType;
    private final Object[] arguments;

    AbstractTerminalOperation(final TerminalOperationType type,
                              final Class<? super S> streamType,
                              final Class<? super R> returnType,
                              final Object... arguments) {
        this.type = requireNonNull(type);
        this.streamType = requireNonNull(streamType);
        this.arguments = requireNonNull(arguments);
        this.returnType = requireNonNull(returnType);
    }

    @Override
    public TerminalOperationType type() {
        return type;
    }

    @Override
    public Class<? super S> streamType() {
        return streamType;
    }

    @Override
    public Object[] arguments() {
        return arguments;
    }

    @Override
    public Class<? super R> returnType() {
        return returnType;
    }

    @Override
    public Function<S, R> function() {
        throw newClassCastException("function");
    }

    @Override
    public ToLongFunction<S> toLongFunction() {
        throw newClassCastException("toLongFunction");
    }

    @Override
    public ToIntFunction<S> toIntFunction() {
        throw newClassCastException("toIntFunction");
    }

    @Override
    public ToDoubleFunction<S> toDoubleFunction() {
        throw newClassCastException("toDoubleFunction");
    }

    @Override
    public Predicate<S> predicate() {
        throw newClassCastException("predicate");
    }

    @Override
    public Consumer<S> consumer() {
        throw newClassCastException("consumer");
    }

    @Override
    public String toString() {
        return String.format("%s(%s)",
                type.toString(),
                arguments == null
                        ? ""
                        : Stream.of(arguments)
                        .map(this::objectLabel)
                        .collect(joining(", "))
        );

    }

    private String objectLabel(Object object) {
        if (object instanceof Long) {
            return object.toString();
        }
        return object.getClass().getSimpleName();
    }

    private ClassCastException newClassCastException(final String methodName) {
        throw new ClassCastException("Unable to apply " + methodName + "() because a terminal operation of type " + type + " does not have a " + methodName + "()");
    }

}