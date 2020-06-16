package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;

import javax.persistence.EntityManagerFactory;

public final class InternalRendererFactory implements RendererFactory {

    @Override
    public Renderer createRenderer(EntityManagerFactory entityManagerFactory) {
        return new StandardRenderer(entityManagerFactory);
    }
}
