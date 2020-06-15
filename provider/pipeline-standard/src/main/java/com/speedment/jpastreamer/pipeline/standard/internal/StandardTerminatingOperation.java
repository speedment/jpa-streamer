package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions.FunctionReturnFunction;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperationType;

import java.util.List;
import java.util.function.*;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class StandardTerminatingOperation<S extends BaseStream<?, S>, R> implements TerminatingOperation<S, R> {

    private final TerminatingOperationType terminatingOperationType;
    private final Class<S> streamType;
    private final List<?> arguments;
    private final Class<R> returnType;
    private final Object function;

    public StandardTerminatingOperation(final TerminatingOperationType terminatingOperationType,
                                        final Class<S> streamType,
                                        final List<?> arguments,
                                        final Class<R> returnType,
                                        final Object function) {
        this.terminatingOperationType = requireNonNull(terminatingOperationType);
        this.streamType = requireNonNull(streamType);
        this.arguments = requireNonNull(arguments);
        this.returnType = requireNonNull(returnType);
        this.function = requireNonNull(function);
    }

    @Override
    public TerminatingOperationType terminatingOperationType() {
        return terminatingOperationType;
    }

    @Override
    public Class<S> streamType() {
        return streamType;
    }

    @Override
    public List<?> arguments() {
        return arguments;
    }

    @Override
    public Class<R> returnType() {
        return returnType;
    }

    @Override
    public Function<S, R> function() {
        return FunctionReturnFunction.of(function);
    }

    @Override
    public LongFunction<S> longFunction() {
        return null;
    }

    @Override
    public IntFunction<S> intFunction() {
        return null;
    }

    @Override
    public DoubleFunction<S> doubleFunction() {
        return null;
    }

    @Override
    public Predicate<S> booleanFunction() {
        return null;
    }

    @Override
    public Consumer<S> consumer() {
        return null;
    }
}
