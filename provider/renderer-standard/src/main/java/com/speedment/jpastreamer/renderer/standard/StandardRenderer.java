package com.speedment.jpastreamer.renderer.standard;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.standard.internal.InternalStandardRenderer;

import javax.persistence.EntityManagerFactory;

public final class StandardRenderer implements Renderer {

    private final Renderer delegate = new InternalStandardRenderer();

    @Override
    public <T> RenderResult<T> render(EntityManagerFactory entityManagerFactory, Pipeline<T> pipeline) {
        return delegate.render(entityManagerFactory, pipeline);
    }
}