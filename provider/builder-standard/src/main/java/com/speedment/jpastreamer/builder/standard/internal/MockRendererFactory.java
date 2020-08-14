package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;

import javax.persistence.EntityManagerFactory;
import java.util.function.BiConsumer;

public final class MockRendererFactory implements RendererFactory {

    private final BiConsumer<Pipeline<?>, RenderResult<?>> listener;

    public MockRendererFactory(BiConsumer<Pipeline<?>, RenderResult<?>> listener) {
        this.listener = listener;
    }

    @Override
    public Renderer createRenderer(EntityManagerFactory entityManagerFactory) {
        return new MockRenderer();
    }

    private final class MockRenderer implements Renderer {

        @Override
        public <T> RenderResult<?> render(Pipeline<T> pipeline) {
            final RenderResult<T> renderResult = null;
            MockRendererFactory.this.listener.accept(pipeline, renderResult);
            return null;
        }

        @Override
        public void close() {}
    }

}
