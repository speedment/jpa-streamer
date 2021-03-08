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
package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.renderer.RenderResult;

import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

final class StandardRenderResult<E, T,  S extends BaseStream<T, S>> implements RenderResult<E, T, S> {

    private final Class<E> root;
    private final S stream;
    private final TerminalOperation<?, ?> terminalOperation;

    StandardRenderResult(
        final Class<E> root,
        final S stream,
        final TerminalOperation<?, ?> terminalOperation
    ) {
        this.root = requireNonNull(root);
        this.stream = requireNonNull(stream);
        this.terminalOperation = requireNonNull(terminalOperation);
    }

    @Override
    public Class<E> root() {
        return root;
    }

    @Override
    public S stream() {
        return stream;
    }

    @Override
    public TerminalOperation<?, ?> terminalOperation() {
        return terminalOperation;
    }
}
