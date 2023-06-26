/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
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

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface HasReturnType<R> {

    /**
     * Returns the return type of the operation.
     * <p>
     * Potential return types for terminating operations include:
     * <ul>
     *     <li>{@code long.class}, e.g. for {@code count()}</li>
     *     <li>{@code Object.class}, e.g. for {@code collect()}</li>
     *     <li>{@code void.class}, e.g. for {@code forEach()}</li>
     *     <li>{@code boolean.class}, e.g. for {@code anyMatch()}</li>
     *     <li>{@code Object[].class}, e.g. for {@code toArray()}</li>
     *     <li>{@code Optional.class}, e.g. for {@code findAny()}</li>
     * </ul>
     * <p>
     * Return types for intermediate operations include:
     * <ul>
     *     <li>{@link Stream}, e.g. for {@code mapToObj()}</li>
     *     <li>{@link IntStream}, e.g. for {@code mapToInt()}</li>
     *     <li>{@link LongStream}, e.g. for {@code mapToLong()}</li>
     *     <li>{@link DoubleStream}, e.g. for {@code mapToDouble()}</li>
     * </ul>
     * @return the return type of the terminating operation
     */
    Class<? super R> returnType();

}
