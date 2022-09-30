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
package com.speedment.jpastreamer.application.standard;

import com.speedment.jpastreamer.application.JPAStreamerBuilder;
import com.speedment.jpastreamer.application.JPAStreamerBuilderFactory;
import com.speedment.jpastreamer.application.standard.internal.StandardJPAStreamerBuilder;

import javax.persistence.EntityManagerFactory;

import static java.util.Objects.requireNonNull;

public final class StandardJPAStreamerBuilderFactory implements JPAStreamerBuilderFactory {

    @Override
    public JPAStreamerBuilder create(final String persistenceUnitName) {
        return new StandardJPAStreamerBuilder(requireNonNull(persistenceUnitName));
    }

    @Override
    public JPAStreamerBuilder create(final EntityManagerFactory entityManagerFactory) {
        return new StandardJPAStreamerBuilder(requireNonNull(entityManagerFactory));
    }
}
