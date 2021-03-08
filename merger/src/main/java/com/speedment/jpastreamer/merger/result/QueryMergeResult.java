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
package com.speedment.jpastreamer.merger.result;

import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.Query;

/**
 * A container object used to store the results of the merge operation
 * performed on a {@code Pipeline}.
 *
 * @param <T> root entity
 * @author Mislav Milicevic
 */
public interface QueryMergeResult<T> {

    /**
     * Returns the pipeline that was used during operation merging.
     *
     * @return the pipeline that was used during operation merging
     */
    Pipeline<T> getPipeline();

    /**
     * Returns the query that the operations were merged into.
     *
     * @return the query that the operations were merged into
     */
    Query getQuery();
}
