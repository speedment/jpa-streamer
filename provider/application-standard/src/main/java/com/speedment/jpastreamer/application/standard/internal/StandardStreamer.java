package com.speedment.jpastreamer.application.standard.internal;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

final class StandardStreamer<E> implements Streamer<E> {

    private final Renderer renderer;
    private final BuilderFactory builderFactory;

    private final Class<E> entityClass;

    StandardStreamer(final Class<E> entityClass, final EntityManagerFactory entityManagerFactory) {
        this.entityClass = requireNonNull(entityClass);
        this.builderFactory = RootFactory.getOrThrow(BuilderFactory.class, ServiceLoader::load);
        this.renderer = RootFactory.getOrThrow(RendererFactory.class, ServiceLoader::load)
            .createRenderer(entityManagerFactory);
    }

    @Override
    public Stream<E> stream() {
        return builderFactory.createBuilder(entityClass, renderer);
    }

    @Override
    public void close() {
        System.out.println("Closing Streamer<" + entityClass.getSimpleName() + ">");
        renderer.close();
    }
}
