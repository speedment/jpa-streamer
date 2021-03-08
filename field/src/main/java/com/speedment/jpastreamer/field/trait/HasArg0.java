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
package com.speedment.jpastreamer.field.trait;

/**
 * A representation of the first argument of a field predicate.
 *
 * @author Julia Gustafsson
 * @since  0.1.0
 */

public interface HasArg0<T0> {

    /**
     * Returns the first argument of a predicate.
     *
     * @return  the first argument
     */
    T0 get0();
}
