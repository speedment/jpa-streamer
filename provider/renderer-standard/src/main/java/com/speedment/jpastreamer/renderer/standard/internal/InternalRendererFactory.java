/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.renderer.standard.internal;

import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Supplier;

public final class InternalRendererFactory implements RendererFactory {

    @Override
    public Renderer createRenderer(final EntityManagerFactory entityManagerFactory) {
        return createRenderer(entityManagerFactory::createEntityManager); 
    }
    
    public Renderer createRenderer(final Supplier<EntityManager> entityManagerSupplier) {
        return new StandardRenderer(entityManagerSupplier); 
    }

    @Override
    public Renderer createRenderer(EntityManager entityManager) {
        return new StandardRenderer(entityManager);
    }
}
