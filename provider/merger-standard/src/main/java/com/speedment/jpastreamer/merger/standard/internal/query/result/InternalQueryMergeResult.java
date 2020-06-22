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

package com.speedment.jpastreamer.merger.standard.internal.query.result;

import com.speedment.jpastreamer.merger.result.QueryMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;

import javax.persistence.Query;

public final class InternalQueryMergeResult<T> implements QueryMergeResult<T> {

    private final Pipeline<T> pipeline;
    private final Query query;

    public InternalQueryMergeResult(
        final Pipeline<T> pipeline,
        final Query query
    ) {
        this.pipeline = pipeline;
        this.query = query;
    }

    @Override
    public Pipeline<T> getPipeline() {
        return pipeline;
    }

    @Override
    public Query getQuery() {
        return query;
    }
}
