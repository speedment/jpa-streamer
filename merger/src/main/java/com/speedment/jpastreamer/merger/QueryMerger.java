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
package com.speedment.jpastreamer.merger;

import com.speedment.jpastreamer.merger.result.QueryMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.Query;


/**
 * A stream operation merger used to apply offsets and limits to provided queries.
 *
 * @author Mislav Milicevic
 */
public interface QueryMerger {

    /**
     * Inspects the provided {@code pipeline} and merges all available operations
     * into the provided {@code query}. Operations are to be removed from the pipeline
     * if they are merged.
     *
     * The modified pipeline and query are stored in a {@code QueryMergeResult} and returned.
     *
     * @param pipeline to inspect and merge
     * @param query that accepts the merged operations
     * @param <T> root entity
     * @return a new QueryMergeResult containing the modified {@code Pipeline}
     *         and {@code Query}
     */
    <T> QueryMergeResult<T> merge(final Pipeline<T> pipeline, final Query query);
}
