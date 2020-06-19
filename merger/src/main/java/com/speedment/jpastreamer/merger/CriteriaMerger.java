/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.jpastreamer.merger;

import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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
     * @param criteriaQuery that accepts the merged operations
     * @param criteriaBuilder used to create conditions
     * @param <T> root entity
     * @return a new CriteriaMergeResult containing the modified {@code Pipeline}
     *         and {@code CriteriaQuery}
     */
    <T> CriteriaMergeResult<T> merge(
        final Pipeline<T> pipeline,
        final CriteriaQuery<T> criteriaQuery,
        final CriteriaBuilder criteriaBuilder
    );
}
