/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import java.util.stream.Stream;

public final class InternalBuilderFactory implements BuilderFactory {

    @Override
    public <T> Stream<T> createBuilder(final StreamConfiguration<T> streamConfiguration, final Renderer renderer) {
        final BaseBuilderState<T> baseState = new BaseBuilderState<>(InjectedFactories.INSTANCE, streamConfiguration, renderer);
        return new StreamBuilder<>(baseState);
    }
}
