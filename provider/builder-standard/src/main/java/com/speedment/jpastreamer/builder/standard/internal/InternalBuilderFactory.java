package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;

import java.util.stream.Stream;

public final class InternalBuilderFactory implements BuilderFactory {

    @Override
    public <T> Stream<T> createBuilder(final Class<T> root, final Renderer renderer) {
        return new StreamBuilder<>(InjectedFactories.INSTANCE, root, renderer);

    }
}