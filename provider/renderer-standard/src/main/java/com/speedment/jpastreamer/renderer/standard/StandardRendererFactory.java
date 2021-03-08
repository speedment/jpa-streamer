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
package com.speedment.jpastreamer.renderer.standard;

import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.renderer.standard.internal.InternalRendererFactory;

import javax.persistence.EntityManagerFactory;

public final class StandardRendererFactory implements RendererFactory {

    private final RendererFactory delegate = new InternalRendererFactory();

    @Override
    public Renderer createRenderer(final EntityManagerFactory entityManagerFactory) {
        return delegate.createRenderer(entityManagerFactory);
    }
}
