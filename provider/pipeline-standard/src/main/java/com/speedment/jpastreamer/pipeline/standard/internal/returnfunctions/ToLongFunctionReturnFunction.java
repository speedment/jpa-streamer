package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.ToLongFunction;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class ToLongFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<ToLongFunction<S>> {

    private static final ToLongFunctionReturnFunction<?> SINGLETON = new ToLongFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public ToLongFunction<S> castToTyped(Object untypedFunction) {
        return (ToLongFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> ToLongFunction<S> cast(final Object object) {
        return ((ToLongFunctionReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(object));
    }

}