package com.speedment.jpastreamer.rootfactory;

import com.speedment.jpastreamer.rootfactory.internal.InternalRootFactory;

import java.util.Optional;
import java.util.stream.Stream;

public final class RootFactory {
    private RootFactory() {}

    public static <T> Optional<T> get(final Class<T> classToken) {
        return InternalRootFactory.get(classToken);
    }

    public static <T> T getOrThrow(final Class<T> classToken) {
        return InternalRootFactory.getOrThrow(classToken);
    }

    public static <S> Stream<S> stream(final Class<S> service) {
        return InternalRootFactory.stream(service);
    }

}