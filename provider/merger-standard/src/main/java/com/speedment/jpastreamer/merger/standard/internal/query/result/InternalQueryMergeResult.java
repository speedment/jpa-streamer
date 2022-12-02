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
