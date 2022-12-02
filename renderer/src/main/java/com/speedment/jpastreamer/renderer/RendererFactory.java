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
package com.speedment.jpastreamer.renderer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Supplier;

public interface RendererFactory {

    /**
     * Creates and returns a new {@code Renderer} whereby the provided {@code entityManagerFactory}
     * is used to create streams.
     *
     * @param entityManagerFactory to be used for creating streams
     * @return a new {@code Renderer} where the provided {@code entityManagerFactory}
     *         is used for creating streams
     */
    @Deprecated
    Renderer createRenderer(final EntityManagerFactory entityManagerFactory);

    /**
     * Creates and returns a new {@code Renderer} whereby the provided {@code entityManagerSupplier}
     * is used to create streams.
     *
     * @param entityManagerSupplier to be used for creating streams
     * @return a new {@code Renderer} where the provided {@code entityManagerSupplier}
     *         is used for creating streams
     */
    Renderer createRenderer(final Supplier<EntityManager> entityManagerSupplier);
    
    Renderer createRenderer(final EntityManager entityManager);
}
