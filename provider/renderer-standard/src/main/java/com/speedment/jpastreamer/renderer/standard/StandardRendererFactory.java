package com.speedment.jpastreamer.renderer.standard;

import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.renderer.standard.internal.InternalRendererFactory;

import javax.persistence.EntityManagerFactory;

public class StandardRendererFactory implements RendererFactory {

    private final RendererFactory delegate = new InternalRendererFactory();

    @Override
    public Renderer createRenderer(EntityManagerFactory entityManagerFactory) {
        return delegate.createRenderer(entityManagerFactory);
    }
}
