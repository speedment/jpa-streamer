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

import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.criteria.CriteriaQuery;

/**
 * A container object used to store the results of the merge operation
 * performed on a {@code Pipeline}.
 *
 * @param <T> root entity
 * @author Mislav Milicevic
 */
public interface MergeResult<T> {

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
    CriteriaQuery<T> getQuery();
}
