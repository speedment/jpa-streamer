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
package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

final class StandardStreamer<E> implements Streamer<E> {

    private final Renderer renderer;
    private final BuilderFactory builderFactory;
    private final AutoCloseFactory autoCloseFactory;
    private final Class<E> entityClass;

    StandardStreamer(final Class<E> entityClass, final EntityManagerFactory entityManagerFactory) {
        this.entityClass = requireNonNull(entityClass);
        requireNonNull(entityManagerFactory);
        this.builderFactory = RootFactory.getOrThrow(BuilderFactory.class, ServiceLoader::load);
        this.autoCloseFactory = RootFactory.getOrThrow(AutoCloseFactory.class, ServiceLoader::load);
        this.renderer = RootFactory.getOrThrow(RendererFactory.class, ServiceLoader::load)
            .createRenderer(entityManagerFactory);
    }

    @Override
    public Stream<E> stream() {
        return autoCloseFactory.createAutoCloseStream(builderFactory.createBuilder(entityClass, renderer));
    }

    @Override
    public void close() {
        //System.out.println("Closing Streamer<" + entityClass.getSimpleName() + ">");
        renderer.close();
    }
}
