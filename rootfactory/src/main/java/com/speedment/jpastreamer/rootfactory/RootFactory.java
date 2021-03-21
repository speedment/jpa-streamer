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
package com.speedment.jpastreamer.rootfactory;

import com.speedment.jpastreamer.rootfactory.internal.InternalRootFactory;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Stream;

public final class RootFactory {
    private RootFactory() {}

    public static <S> Optional<S> get(final Class<S> classToken, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.get(classToken, loader);
    }

    public static <S> S getOrThrow(final Class<S> classToken, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.getOrThrow(classToken, loader);
    }

    public static <S> Stream<S> stream(final Class<S> service, final Function<Class<S>, ServiceLoader<S>> loader) {
        return InternalRootFactory.stream(service, loader);
    }

}
