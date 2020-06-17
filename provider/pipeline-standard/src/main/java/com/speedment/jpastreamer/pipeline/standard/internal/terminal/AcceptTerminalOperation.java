package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.Consumer;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class AcceptTerminalOperation<S extends BaseStream<?, S>, R, F extends Consumer<S>>
        extends AbstractTerminalOperation<S, R>
        implements TerminalOperation<S, R> {

    private final F consumer;

    AcceptTerminalOperation(final TerminalOperationType type,
                            final Class<? super S> streamType,
                            final Class<? super R> returnType,
                            final F consumer,
                            final Object... arguments) {
        super(type, streamType, returnType, arguments);
        this.consumer = requireNonNull(consumer);
    }

    @Override
    public Consumer<S> consumer() {
        return consumer;
    }
}