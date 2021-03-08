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
package com.speedment.jpastreamer.streamconfiguration.standard;

import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import com.speedment.jpastreamer.streamconfiguration.StreamConfigurationFactory;
import com.speedment.jpastreamer.streamconfiguration.standard.internal.StandardStreamConfiguration;

import static java.util.Objects.requireNonNull;

public final class StandardStreamConfigurationFactory implements StreamConfigurationFactory {

    @Override
    public <T> StreamConfiguration<T> createStreamConfiguration(final Class<T> entityClass) {
        return new StandardStreamConfiguration<>(requireNonNull(entityClass));
    }
}
