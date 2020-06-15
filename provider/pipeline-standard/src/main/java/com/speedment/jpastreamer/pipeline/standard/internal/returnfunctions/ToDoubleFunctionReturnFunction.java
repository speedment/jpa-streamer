package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.DoubleFunction;
import java.util.function.LongFunction;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class DoubleFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<DoubleFunction<S>> {

    private static final DoubleFunctionReturnFunction<?> SINGLETON = new DoubleFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public DoubleFunction<S> castToTyped(Object untypedFunction) {
        return (DoubleFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> DoubleFunction<S> cast(final Object object) {
        return ((DoubleFunctionReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(object));
    }

}