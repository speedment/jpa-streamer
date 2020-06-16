package com.speedment.jpastreamer.builder.standard;

import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.builder.standard.internal.InternalBuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;

import java.util.stream.Stream;

public final class StandardBuilderFactory implements BuilderFactory {

    private final BuilderFactory delegate;

    public StandardBuilderFactory() {
        this.delegate = new InternalBuilderFactory();
    }

    @Override
    public <T> Stream<T> createBuilder(final Class<T> root, Renderer renderer) {
        return delegate.createBuilder(root, renderer);
    }
}