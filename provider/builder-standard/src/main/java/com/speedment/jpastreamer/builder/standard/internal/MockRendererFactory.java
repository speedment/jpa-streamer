/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

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
        public <T> RenderResult<?> render(Pipeline<T> pipeline, StreamConfiguration<T> streamConfiguration) {
            final RenderResult<T> renderResult = null;
            MockRendererFactory.this.listener.accept(pipeline, renderResult);
            return null;
        }

        @Override
        public void close() {}
    }

}
