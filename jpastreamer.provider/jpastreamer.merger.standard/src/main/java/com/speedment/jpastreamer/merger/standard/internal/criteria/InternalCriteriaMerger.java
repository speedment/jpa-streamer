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
package com.speedment.jpastreamer.merger.standard.internal.criteria;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.*;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.standard.internal.criteria.result.InternalCriteriaMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.DistinctCriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.FilterCriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.CriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.criteria.strategy.SortedCriteriaModifier;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.*;

public final class InternalCriteriaMerger implements CriteriaMerger {

    private final Map<IntermediateOperationType, CriteriaModifier> mergingStrategies = new EnumMap<>(IntermediateOperationType.class);

    public InternalCriteriaMerger() {
        registerMergingStrategy(FILTER, FilterCriteriaModifier.INSTANCE);
        registerMergingStrategy(DISTINCT, DistinctCriteriaModifier.INSTANCE);
        registerMergingStrategy(SORTED, SortedCriteriaModifier.INSTANCE);
    }

    @Override
    public <ENTITY> CriteriaMergeResult<ENTITY> merge(
        final Pipeline<ENTITY> pipeline,
        final Criteria<ENTITY, ?> criteria
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

            if (criteriaModifier != null) {

                final IntermediateOperationReference operationReference =
                        IntermediateOperationReference.createReference(operation, i, intermediateOperations);

                criteriaModifier.modifyCriteria(operationReference, criteria, mergingTracker);
                
            }

            if (!mergingTracker.mergedOperations().contains(operationType)) {
                // If the current operation was not merged, the following operations cannot be merged
                // as that risks changing the order in which the operations are applied
                break;
            }
            
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
