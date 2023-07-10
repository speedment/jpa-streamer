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
package com.speedment.jpastreamer.merger.standard.internal.query;

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

import jakarta.persistence.Query;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.LIMIT;
import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.SKIP;
import static java.util.Objects.requireNonNull;

public final class InternalQueryMerger implements QueryMerger {

    private final Map<IntermediateOperationType, QueryModifier> mergingStrategies = new EnumMap<>(IntermediateOperationType.class);

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
                break;
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
