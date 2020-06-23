package com.speedment.jpastreamer.renderer.standard;

import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.renderer.standard.internal.InternalRendererFactory;

import javax.persistence.EntityManagerFactory;

public final class StandardRendererFactory implements RendererFactory {

    private final RendererFactory delegate = new InternalRendererFactory();

    @Override
    public Renderer createRenderer(final EntityManagerFactory entityManagerFactory) {
        return delegate.createRenderer(entityManagerFactory);
    }
}
