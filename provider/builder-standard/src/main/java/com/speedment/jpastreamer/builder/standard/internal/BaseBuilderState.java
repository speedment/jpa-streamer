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
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import static java.util.Objects.requireNonNull;

final class BaseBuilderState<E> {

    private final Factories factories;
    private final StreamConfiguration<E> streamConfiguration;
    private final Pipeline<E> pipeline;
    private final Renderer renderer;

    BaseBuilderState(final Factories factories,
                     final StreamConfiguration<E> streamConfiguration,
                     final Renderer renderer) {
        this.factories = requireNonNull(factories);
        this.streamConfiguration = requireNonNull(streamConfiguration);
        this.renderer = requireNonNull(renderer);
        this.pipeline = InjectedFactories.INSTANCE.pipeline().createPipeline(streamConfiguration.entityClass());
    }

    Factories factories() {
        return factories;
    }

    StreamConfiguration<E> streamConfiguration() {
        return streamConfiguration;
    }

    Pipeline<E> pipeline() {
        return pipeline;
    }

    Renderer renderer() {
        return renderer;
    }

}