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
package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFunctionalType;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.Predicate;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class TestTerminalOperation<S extends BaseStream<?, S>, R, F extends Predicate<S>>
        extends AbstractTerminalOperation<S, R>
        implements TerminalOperation<S, R> {

    private final F predicate;

    TestTerminalOperation(final TerminalOperationType type,
                          final Class<? super S> streamType,
                          final Class<? super R> returnType,
                          final F predicate,
                          final Object... arguments) {
        super(type, streamType, returnType, arguments);
        assert type.functionalType() == TerminalOperationFunctionalType.TEST;
        this.predicate = requireNonNull(predicate);
    }


    @Override
    public Predicate<S> predicate() {
        return predicate;
    }
}
