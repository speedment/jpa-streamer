package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.DoubleFunction;
import java.util.function.LongFunction;
import java.util.stream.BaseStream;

public final class DoubleFunctionReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<DoubleFunction<S>> {

    private static final DoubleFunctionReturnFunction<?> SINGLETON = new DoubleFunctionReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public DoubleFunction<S> castToTyped(Object untypedFunction) {
        return (DoubleFunction<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    static <S extends BaseStream<?, S>> DoubleFunctionReturnFunction<S> of() {
        return (DoubleFunctionReturnFunction<S>) SINGLETON;
    }

}