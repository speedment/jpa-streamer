package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions.*;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

final class StandardTerminalOperation<S extends BaseStream<?, S>, R> implements TerminalOperation<S, R> {

    private final TerminalOperationType type;
    private final Class<? super S> streamType;
    private final Class<? super R> returnType;
    private final Object function;
    private final Object[] arguments;

    StandardTerminalOperation(final TerminalOperationType type,
                              final Class<? super S> streamType,
                              final Class<? super R> returnType,
                              final Object function,
                              final Object... arguments) {
        this.type = requireNonNull(type);
        this.streamType = requireNonNull(streamType);
        this.arguments = requireNonNull(arguments);
        this.returnType = requireNonNull(returnType);
        this.function = requireNonNull(function);
    }

    @Override
    public TerminalOperationType type() {
        return type;
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
        return String.format("%s(%s)",
                type.toString(),
                arguments == null
                        ? ""
                        : Stream.of(arguments)
                        .map(this::objectLabel)
                        .collect(joining(", "))
        );

    }

    private String objectLabel(Object object) {
        if (object instanceof Long) {
            return object.toString();
        }
        return object.getClass().getSimpleName();
    }
}