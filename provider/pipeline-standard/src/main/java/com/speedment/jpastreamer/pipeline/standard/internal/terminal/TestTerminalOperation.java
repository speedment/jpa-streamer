package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.Predicate;
import java.util.function.ToLongFunction;
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
        this.predicate = requireNonNull(predicate);
    }


    @Override
    public Predicate<S> predicate() {
        return predicate;
    }
}