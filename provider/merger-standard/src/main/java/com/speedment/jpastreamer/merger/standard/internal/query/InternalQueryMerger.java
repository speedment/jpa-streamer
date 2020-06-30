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

package com.speedment.jpastreamer.merger.standard.internal.query;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.LIMIT;
import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.SKIP;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.merger.result.QueryMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.query.result.InternalQueryMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.query.strategy.QueryModifier;
import com.speedment.jpastreamer.merger.standard.internal.query.strategy.SkipLimitModifier;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import javax.persistence.Query;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InternalQueryMerger implements QueryMerger {

    private final Map<IntermediateOperationType, QueryModifier> mergingStrategies = new HashMap<>();

    public InternalQueryMerger() {
        registerMergingStrategy(SKIP, SkipLimitModifier.INSTANCE);
        registerMergingStrategy(LIMIT, SkipLimitModifier.INSTANCE);
    }

    @Override
    public <T> QueryMergeResult<T> merge(final Pipeline<T> pipeline, final Query query) {
        requireNonNull(pipeline);
        requireNonNull(query);

        final MergingTracker mergingTracker = MergingTracker.createTracker();

        final List<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

        for (int i = 0; i < intermediateOperations.size(); i++) {
            final IntermediateOperation<?, ?> operation = intermediateOperations.get(i);
            final IntermediateOperationType operationType = operation.type();

            if (mergingTracker.mergedOperations().contains(operationType)) {
                continue;
            }

            final QueryModifier queryModifier = mergingStrategies.get(operationType);

            if (queryModifier == null) {
                continue;
            }

            final IntermediateOperationReference operationReference =
                    IntermediateOperationReference.createReference(operation, i, intermediateOperations);

            queryModifier.modifyQuery(operationReference, query, mergingTracker);
        }

        mergingTracker.forRemoval()
            .stream()
            .sorted(Comparator.reverseOrder())
            .forEach(idx -> intermediateOperations.remove((int) idx));

        return new InternalQueryMergeResult<>(pipeline, query);
    }

    private void registerMergingStrategy(final IntermediateOperationType operationType, final QueryModifier queryModifier) {
        mergingStrategies.put(operationType, queryModifier);
    }
}
