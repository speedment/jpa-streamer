package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.ToDoubleFunction;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class ToDoubleFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<ToDoubleFunction<S>> {

    private static final ToDoubleFunctionReturnFunction<?> SINGLETON = new ToDoubleFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public ToDoubleFunction<S> castToTyped(Object untypedFunction) {
        return (ToDoubleFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> ToDoubleFunction<S> cast(final Object object) {
        return ((ToDoubleFunctionReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(object));
    }

}