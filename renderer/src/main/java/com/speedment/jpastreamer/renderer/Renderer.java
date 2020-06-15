package com.speedment.jpastreamer.renderer;

import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.EntityManagerFactory;

public interface Renderer {

    /**
     * Creates and returns a new RenderResult whereby the provided {@code Pipeline}
     * is rendered to a stream using the provided {@code entityManagerFactory}.
     *
     * @param entityManagerFactory to be used for creating streams
     * @param pipeline describing the intended Stream
     * @param <T> type of the elements in the returned Stream
     * @return a new RenderResult whereby the provided {@code Pipeline}
     *         is rendered to a stream using the provided {@code entityManagerFactory}
     */
    <T> RenderResult<T> render(EntityManagerFactory entityManagerFactory, Pipeline<T> pipeline);

}