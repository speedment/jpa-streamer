package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.renderer.RenderResult;

import java.util.stream.Stream;

public class StandardRenderResult<T> implements RenderResult<T> {

    private final Stream<T> stream;
    private final TerminalOperation<?, ?> terminalOperation;

    public StandardRenderResult(Stream<T> stream, TerminalOperation<?, ?> terminalOperation) {
        this.stream = stream;
        this.terminalOperation = terminalOperation;
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
