package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.builder.BuilderFactory;

import java.util.stream.Stream;

public final class InternalBuilderFactory implements BuilderFactory {

    @Override
    public <T> Stream<T> createBuilder(final Class<T> root) {
        throw new UnsupportedOperationException("todo");
    }
}
