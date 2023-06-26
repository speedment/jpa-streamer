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
package com.speedment.jpastreamer.merger;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;

/**
 * A stream operation merger used to apply conditions to provided queries.
 *
 * @author Mislav Milicevic
 */
public interface CriteriaMerger {

    /**
     * Inspects the provided {@code pipeline} and merges all available operations
     * into the provided {@code query}. Operations are to be removed from the pipeline
     * if they are merged.
     *
     * The modified pipeline and query are stored in a {@code CriteriaMergeResult} and returned.
     *
     * @param pipeline to inspect and merge
     * @param criteria that provides access to the underlying builder and query
     * @param <ENTITY> root entity
     * @return a new CriteriaMergeResult containing the modified {@code Pipeline}
     *         and {@code CriteriaQuery}
     */
    <ENTITY> CriteriaMergeResult<ENTITY> merge(
        final Pipeline<ENTITY> pipeline,
        final Criteria<ENTITY, ?> criteria
    );
}
