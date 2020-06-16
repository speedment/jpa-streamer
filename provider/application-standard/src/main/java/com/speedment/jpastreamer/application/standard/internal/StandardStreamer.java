package com.speedment.jpastreamer.application.standard.internal;

import javax.persistence.EntityManagerFactory;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

final class StandardStreamer<E> implements Streamer<E> {

    private final Renderer renderer;
    private final PipelineFactory pipelineFactory;

    private final Class<E> entityClass;

    StandardStreamer(final Class<E> entityClass, final EntityManagerFactory entityManagerFactory) {
        this.entityClass = requireNonNull(entityClass);
        this.pipelineFactory = RootFactory.getOrThrow(PipelineFactory.class);
        this.renderer = RootFactory.getOrThrow(RendererFactory.class)
            .createRenderer(entityManagerFactory);
    }

    @Override
    public Stream<E> stream() {
        final Pipeline<E> pipeline = pipelineFactory.createPipeline(entityClass);

        return renderer.render(pipeline).stream();
    }

    @Override
    public void close() {
        System.out.println("Closing Streamer<" + entityClass.getSimpleName() + ">");
        renderer.close();
    }
}
