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
package com.speedment.jpastreamer.builder.standard;

import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.builder.standard.internal.InternalBuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;

import java.util.stream.Stream;

public final class StandardBuilderFactory implements BuilderFactory {

    private final BuilderFactory delegate;

    public StandardBuilderFactory() {
        this.delegate = new InternalBuilderFactory();
    }

    @Override
    public <T> Stream<T> createBuilder(final Class<T> root, Renderer renderer) {
        return delegate.createBuilder(root, renderer);
    }
}