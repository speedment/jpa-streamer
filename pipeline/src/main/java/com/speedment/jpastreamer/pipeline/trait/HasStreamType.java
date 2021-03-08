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
package com.speedment.jpastreamer.pipeline.trait;

import java.util.stream.*;

public interface HasStreamType<S extends BaseStream<?, S>> {

    /**
     * Returns the stream type on which the operation
     * is supposed to be invoked.
     * <p>
     * Any of the following classes can be returned:
     * <ul>
     *     <li>{@link Stream}</li>
     *     <li>{@link IntStream}</li>
     *     <li>{@link LongStream}</li>
     *     <li>{@link DoubleStream}</li>
     * </ul>
     *
     * @return the stream type on which the operation
     *         is supposed to be invoked
     */
    Class<? super S> streamType();

}
