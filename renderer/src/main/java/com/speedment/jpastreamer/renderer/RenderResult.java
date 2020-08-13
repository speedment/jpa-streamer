package com.speedment.jpastreamer.renderer;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.stream.Stream;

public interface RenderResult<T> {

    Class<T> root();

    Stream<T> stream();

    TerminalOperation<?, ?> terminalOperation();

}
