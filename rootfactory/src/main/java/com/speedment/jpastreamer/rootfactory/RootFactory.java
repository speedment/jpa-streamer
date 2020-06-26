package com.speedment.jpastreamer.rootfactory;

import com.speedment.jpastreamer.rootfactory.internal.InternalRootFactory;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Stream;

public final class RootFactory {
    private RootFactory() {}

    public static <S> Optional<S> get(final Class<S> classToken, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.get(classToken, loader);
    }

    public static <S> S getOrThrow(final Class<S> classToken, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.getOrThrow(classToken, loader);
    }

    public static <S> Stream<S> stream(final Class<S> service, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.stream(service, loader);
    }

}