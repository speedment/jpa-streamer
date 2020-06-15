package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.Consumer;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class ConsumerReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<Consumer<S>> {

    private static final ConsumerReturnFunction<?> SINGLETON = new ConsumerReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public Consumer<S> castToTyped(Object untypedFunction) {
        return (Consumer<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> Consumer<S> cast(final Object function) {
        return ((ConsumerReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(function));
    }

}