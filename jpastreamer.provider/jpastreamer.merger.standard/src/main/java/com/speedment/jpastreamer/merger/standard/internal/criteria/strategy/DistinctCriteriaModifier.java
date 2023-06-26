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
package com.speedment.jpastreamer.merger.standard.internal.criteria.strategy;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.DISTINCT;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

public enum DistinctCriteriaModifier implements CriteriaModifier {

    INSTANCE;

    @Override
    public <ENTITY> void modifyCriteria(
        final IntermediateOperationReference operationReference,
        final Criteria<ENTITY, ?> criteria,
        final MergingTracker mergingTracker
    ) {
        requireNonNull(operationReference);
        requireNonNull(criteria);
        requireNonNull(mergingTracker);

        final IntermediateOperation<?, ?> operation = operationReference.get();

        final IntermediateOperationType operationType = operation.type();

        if (operationType != DISTINCT) {
            return;
        }

        criteria.getQuery().distinct(true);

        mergingTracker.markAsMerged(operationType);
        mergingTracker.markForRemoval(operationReference.index());
    }
}
