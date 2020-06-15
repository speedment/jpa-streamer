package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

import java.util.function.Predicate;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

public final class PredicateReturnFunction<S extends BaseStream<?, S>> implements ReturnFunction<Predicate<S>> {

    private static final PredicateReturnFunction<?> SINGLETON = new PredicateReturnFunction<>();

    @Override
    @SuppressWarnings("unchecked")
    public Predicate<S> castToTyped(Object untypedFunction) {
        return (Predicate<S>) untypedFunction;
    }

    @SuppressWarnings("unchecked")
    public static <S extends BaseStream<?, S>> Predicate<S> cast(final Object object) {
        return ((PredicateReturnFunction<S>) SINGLETON).castToTyped(requireNonNull(object));
    }

}