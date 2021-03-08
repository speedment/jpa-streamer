/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.pipeline.standard.internal.intermediate;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

final class StandardIntermediateOperation<S extends BaseStream<?, S>, R extends BaseStream<?, R>>
        implements IntermediateOperation<S, R> {

    private final IntermediateOperationType type;
    private final Class<? super S> streamType;
    private final Class<? super R> returnType;
    private final Function<S, R> function;
    private final Object[] arguments;

    StandardIntermediateOperation(final IntermediateOperationType type,
                                  final Class<? super S> streamType,
                                  final Class<? super R> returnType,
                                  final Function<S, R> function,
                                  final Object... arguments) {
        this.type = requireNonNull(type);
        this.streamType = requireNonNull(streamType);
        this.arguments = requireNonNull(arguments);
        this.returnType = requireNonNull(returnType);
        this.function = requireNonNull(function);
    }

    @Override
    public IntermediateOperationType type() {
        return type;
    }

    @Override
    public Class<? super S> streamType() {
        return streamType;
    }

    @Override
    public Class<? super R> returnType() {
        return returnType;
    }

    @Override
    public Function<S, R> function() {
        return function;
    }

    @Override
    public Object[] arguments() {
        return arguments;
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


}
