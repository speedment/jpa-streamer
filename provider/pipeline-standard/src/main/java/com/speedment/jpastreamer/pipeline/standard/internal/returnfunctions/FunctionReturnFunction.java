package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.Function;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class FunctionReturnFunction<S extends BaseStream<?, S>, R> implements ReturnFunction<Function<S, R>> {

    private static final FunctionReturnFunction<?, ?> SINGLETON = new FunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public Function<S, R> castToTyped(Object untypedFunction) {
        return (Function<S, R>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>, R> Function<S, R> cast(final Object function) {
        return ((FunctionReturnFunction<S, R>) SINGLETON).castToTyped(requireNonNull(function));
    }

}