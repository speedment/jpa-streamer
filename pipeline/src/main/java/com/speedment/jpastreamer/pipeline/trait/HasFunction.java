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
package com.speedment.jpastreamer.pipeline.trait;

import java.util.function.Function;

public interface HasFunction<S, R> {

    /**
     * Returns a function, that when applied, will convert
     * from a source stream of type S to some result of type R.
     *
     * @return a function, that when applied, will convert
     *         from a source stream of type S to some result of type R
     *
     * @throws ClassCastException if the operation
     *         is not applicable (e.g. a terminating operation
     *         does not have a function but perhaps a consumer
     */
    Function<S, R> function();
}
