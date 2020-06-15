package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.ToIntFunction;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class ToIntFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<ToIntFunction<S>> {

    private static final ToIntFunctionReturnFunction<?> SINGLETON = new ToIntFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public ToIntFunction<S> castToTyped(Object untypedFunction) {
        return (ToIntFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> ToIntFunction<S> cast(final Object function) {
        return ((ToIntFunctionReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(function));
    }

}