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
package com.speedment.jpastreamer.termopoptimizer;

import com.speedment.jpastreamer.pipeline.Pipeline;

@FunctionalInterface
public interface TerminalOperationOptimizer {

    /**
     * Potentially optimizes the provided {@code pipeline} depending
     * on the terminal operation.
     *
     * @param pipeline to modify
     * @param <T> pipeline type
     * @return the modified pipeline
     */
    <T> Pipeline<T> optimize(Pipeline<T> pipeline);

}