package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFunctionalType;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.Function;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class ApplyTerminalOperation<S extends BaseStream<?, S>, R, F extends Function<S, ? super R>>
        extends AbstractTerminalOperation<S, R>
        implements TerminalOperation<S, R> {

    private final F function;

    ApplyTerminalOperation(final TerminalOperationType type,
                           final Class<? super S> streamType,
                           final Class<? super R> returnType,
                           final F function,
                           final Object... arguments) {
        super(type, streamType, returnType, arguments);
        assert type.functionalType() == TerminalOperationFunctionalType.APPLY;
        this.function = requireNonNull(function);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<S, R> function() {
        return (Function<S, R>) function;
    }

}