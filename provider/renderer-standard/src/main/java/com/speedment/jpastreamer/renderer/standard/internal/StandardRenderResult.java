package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.renderer.RenderResult;

import java.util.stream.Stream;

public class StandardRenderResult<T> implements RenderResult<T> {

    private final Class<T> returnType;
    private final Stream<T> stream;
    private final TerminalOperation<?, ?> terminalOperation;

    public StandardRenderResult(
        final Class<T> returnType,
        final Stream<T> stream,
        final TerminalOperation<?, ?> terminalOperation
    ) {
        this.stream = stream;
        this.returnType = returnType;
        this.terminalOperation = terminalOperation;
    }

    @Override
    public Class<T> root() {
        return returnType;
    }

    @Override
    public Stream<T> stream() {
        return stream;
    }

    @Override
    public TerminalOperation<?, ?> terminalOperation() {
        return terminalOperation;
    }
}
