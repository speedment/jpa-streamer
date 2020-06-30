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

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.DISTINCT;
import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.FILTER;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.standard.internal.criteria.result.InternalCriteriaMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.DistinctCriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.FilterCriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.CriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InternalCriteriaMerger implements CriteriaMerger {

    private final Map<IntermediateOperationType, CriteriaModifier> mergingStrategies = new HashMap<>();

    public InternalCriteriaMerger() {
        registerMergingStrategy(FILTER, FilterCriteriaModifier.INSTANCE);
        registerMergingStrategy(DISTINCT, DistinctCriteriaModifier.INSTANCE);
    }

    @Override
    public <T> CriteriaMergeResult<T> merge(
        final Pipeline<T> pipeline,
        final Criteria<T> criteria
    ) {
        requireNonNull(pipeline);
        requireNonNull(criteria);

        final MergingTracker mergingTracker = MergingTracker.createTracker();

        final List<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

        for (int i = 0; i < intermediateOperations.size(); i++) {
            final IntermediateOperation<?, ?> operation = intermediateOperations.get(i);
            final IntermediateOperationType operationType = operation.type();

            if (mergingTracker.mergedOperations().contains(operationType)) {
                continue;
            }

            final CriteriaModifier criteriaModifier = mergingStrategies.get(operationType);

            if (criteriaModifier == null) {
                continue;
            }

            final IntermediateOperationReference operationReference =
                    IntermediateOperationReference.createReference(operation, i, intermediateOperations);

            criteriaModifier.modifyCriteria(operationReference, criteria, mergingTracker);
        }

        mergingTracker.forRemoval()
            .stream()
            .sorted(Comparator.reverseOrder())
            .forEach(idx -> intermediateOperations.remove((int) idx));

        return new InternalCriteriaMergeResult<>(pipeline, criteria);
    }

    private void registerMergingStrategy(final IntermediateOperationType operationType, final CriteriaModifier criteriaModifier) {
        mergingStrategies.put(operationType, criteriaModifier);
    }
}
