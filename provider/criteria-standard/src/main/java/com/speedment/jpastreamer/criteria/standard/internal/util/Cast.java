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
package com.speedment.jpastreamer.criteria.standard.internal.util;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Per Minborg
 * @since 0.0.9
 */
public final class Cast {

    private Cast() {
    }

    /**
     * Casts and returns the provided object if it is assignable from the given
     * class, otherwise returns an Optional.empty().
     *
     * @param <T> the type to return
     * @param object to cast
     * @param clazz to cast to
     * @return An Optional of the casted element or Optional.empty()
     */
    public static <T> Optional<T> cast(Object object, Class<T> clazz) {
        requireNonNull(clazz);
        if (object == null) {
            return Optional.empty();
        }
        if (clazz.isAssignableFrom(object.getClass())) {
            final T result = clazz.cast(object);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    /**
     * Casts and returns the provided object to the provided class.
     *
     * @param <T> the type to return
     * @param object to cast
     * @param clazz to cast to
     * @return the casted element
     * @throws NoSuchElementException if the object could not be casted to the
     * provided class
     */
    public static <T> T castOrFail(Object object, Class<T> clazz) {
        requireNonNull(clazz);
        if (object == null) {
            throw new NoSuchElementException("null is not an instance of " + clazz.getName());
        }
        return Optional.of(object)
            .filter(o -> clazz.isAssignableFrom(o.getClass()))
            .map(clazz::cast)
            .orElseThrow(NoSuchElementException::new);
    }
}
