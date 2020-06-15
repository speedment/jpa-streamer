package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperator;

import java.util.function.Function;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class StandardIntermediateOperator<S extends BaseStream<?, S>, R extends BaseStream<?, R>>
        implements IntermediateOperator<S, R> {

    private final IntermediateOperationType type;
    private final Class<? super S> streamType;
    private final Class<? super R> returnType;
    private final Function<S, R> function;
    private final Object[] arguments;

    StandardIntermediateOperator(final IntermediateOperationType type,
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

}