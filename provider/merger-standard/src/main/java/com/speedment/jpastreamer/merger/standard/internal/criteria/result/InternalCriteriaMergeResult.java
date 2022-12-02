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
package com.speedment.jpastreamer.merger.standard.internal.criteria.result;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;

public final class InternalCriteriaMergeResult<ENTITY> implements CriteriaMergeResult<ENTITY> {

    private final Pipeline<ENTITY> pipeline;
    private final Criteria<ENTITY, ?> criteria;

    public InternalCriteriaMergeResult(
        final Pipeline<ENTITY> pipeline,
        final Criteria<ENTITY, ?> criteria
    ) {
        this.pipeline = pipeline;
        this.criteria = criteria;
    }

    @Override
    public Pipeline<ENTITY> getPipeline() {
        return pipeline;
    }

    @Override
    public Criteria<ENTITY, ?> getCriteria() {
        return criteria;
    }
}
