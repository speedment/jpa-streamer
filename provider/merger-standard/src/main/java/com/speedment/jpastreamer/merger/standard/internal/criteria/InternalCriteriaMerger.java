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

package com.speedment.jpastreamer.merger.standard.internal.criteria;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.standard.internal.criteria.result.InternalCriteriaMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.FilterCriteriaMerger;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InternalCriteriaMerger implements CriteriaMerger {

    private final List<CriteriaMerger> mergingStrategies = new ArrayList<>();

    public InternalCriteriaMerger() {
        registerMergingStrategy(FilterCriteriaMerger.INSTANCE);
    }

    @Override
    public <T> CriteriaMergeResult<T> merge(
        final Pipeline<T> pipeline,
        final Criteria<T> criteria
    ) {
        requireNonNull(pipeline);
        requireNonNull(criteria);

        CriteriaMergeResult<T> result = new InternalCriteriaMergeResult<>(pipeline, criteria);

        for (CriteriaMerger merger : mergingStrategies) {
            result = merger.merge(result.getPipeline(), result.getCriteria());
        }

        return result;
    }

    private void registerMergingStrategy(final CriteriaMerger criteriaMerger) {
        mergingStrategies.add(criteriaMerger);
    }
}
