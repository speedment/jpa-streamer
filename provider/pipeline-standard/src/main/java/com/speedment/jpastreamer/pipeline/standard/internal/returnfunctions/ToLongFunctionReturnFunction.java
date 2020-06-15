package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.LongFunction;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class LongFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<LongFunction<S>> {

    private static final LongFunctionReturnFunction<?> SINGLETON = new LongFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public LongFunction<S> castToTyped(Object untypedFunction) {
        return (LongFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> LongFunction<S> cast(final Object object) {
        return ((LongFunctionReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(object));
    }

}