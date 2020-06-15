package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;

import javax.persistence.EntityManagerFactory;

public class InternalStandardRenderer implements Renderer {

    @Override
    public <T> RenderResult<T> render(EntityManagerFactory entityManagerFactory, Pipeline<T> pipeline) {
        throw new UnsupportedOperationException("todo");
    }
}