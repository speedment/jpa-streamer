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
package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface JPAStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void close();

    static JPAStreamerBuilder createJPAStreamerBuilder(final String persistenceUnitName) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(persistenceUnitName);
    }

    static JPAStreamerBuilder createJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(entityManagerFactory);
    }

}
