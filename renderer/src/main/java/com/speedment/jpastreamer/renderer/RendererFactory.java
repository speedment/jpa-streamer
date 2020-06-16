package com.speedment.jpastreamer.renderer;

import javax.persistence.EntityManagerFactory;

public interface RendererFactory {

    /**
     * Creates and returns a new {@code Renderer} whereby the provided {@code entityManagerFactory}
     * is used to create streams.
     *
     * @param entityManagerFactory to be used for creating streams
     * @return a new {@code Renderer} where the provided {@code entityManagerFactory}
     *         is used for creating streams
     */
    Renderer createRenderer(EntityManagerFactory entityManagerFactory);
}
