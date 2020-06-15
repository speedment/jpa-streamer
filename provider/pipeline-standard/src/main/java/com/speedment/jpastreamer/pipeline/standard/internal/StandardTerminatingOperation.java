package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions.*;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperationType;

import java.util.function.*;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class StandardTerminatingOperation<S extends BaseStream<?, S>, R> implements TerminatingOperation<S, R> {

    private final TerminatingOperationType terminatingOperationType;
    private final Class<? super S> streamType;
    private final Class<? super R> returnType;
    private final Object function;
    private final Object[] arguments;

    StandardTerminatingOperation(final TerminatingOperationType terminatingOperationType,
                                        final Class<? super S> streamType,
                                        final Class<? super R> returnType,
                                        final Object function,
                                        final Object... arguments) {
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
    public Class<? super S> streamType() {
        return streamType;
    }

    @Override
    public Object[] arguments() {
        return arguments;
    }

    @Override
    public Class<? super R> returnType() {
        return returnType;
    }

    @Override
    public Function<S, R> function() {
        return FunctionReturnFunction.cast(function);
    }

    @Override
    public ToLongFunction<S> toLongFunction() {
        return ToLongFunctionReturnFunction.cast(function);
    }

    @Override
    public ToIntFunction<S> toIntFunction() {
        return ToIntFunctionReturnFunction.cast(function);
    }

    @Override
    public ToDoubleFunction<S> toDoubleFunction() {
        return ToDoubleFunctionReturnFunction.cast(function);
    }

    @Override
    public Predicate<S> predicate() {
        return PredicateReturnFunction.cast(function);
    }

    @Override
    public Consumer<S> consumer() {
        return ConsumerReturnFunction.cast(function);
    }

    @Override
    public String toString() {
        return "StandardTerminatingOperation{" +
                "terminatingOperationType=" + terminatingOperationType +
                "argLength=" + (arguments == null ? 0 : arguments.length) +
                '}';
    }
}