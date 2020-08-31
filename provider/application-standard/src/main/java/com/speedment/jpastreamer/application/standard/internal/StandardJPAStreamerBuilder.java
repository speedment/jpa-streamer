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

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.application.JPAStreamerBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static java.util.Objects.requireNonNull;

public final class StandardJPAStreamerBuilder implements JPAStreamerBuilder {

    private final EntityManagerFactory entityManagerFactory;
    private final boolean closeEntityManager;

    public StandardJPAStreamerBuilder(final String persistenceUnitName) {
        this.closeEntityManager = true;
        this.entityManagerFactory = Persistence.createEntityManagerFactory(requireNonNull(persistenceUnitName));
    }

    public StandardJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        this.closeEntityManager = false;
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
    }

    @Override
    public JPAStreamer build() {
        return new StandardJPAStreamer(entityManagerFactory, closeEntityManager);
    }

}