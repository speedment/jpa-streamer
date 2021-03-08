/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.merger.standard.internal.query.strategy;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.LIMIT;
import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.SKIP;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import javax.persistence.Query;
import java.util.Optional;

public enum SkipLimitModifier implements QueryModifier {

    INSTANCE;

    @Override
    public void modifyQuery(
        final IntermediateOperationReference operationReference,
        final Query query,
        final MergingTracker mergingTracker
    ) {
        requireNonNull(operationReference);
        requireNonNull(query);
        requireNonNull(mergingTracker);

        final IntermediateOperation<?, ?> operation = operationReference.get();

        final IntermediateOperationType operationType = operation.type();

        if (operationType != SKIP && operationType != LIMIT) {
            return;
        }

        if (operationType == SKIP) {
            if (mergingTracker.mergedOperations().contains(LIMIT)) {
                return;
            }

            query.setFirstResult((int) getArgument(operation));
            mergingTracker.markAsMerged(operationType);
            mergingTracker.markForRemoval(operationReference.index());

            final Optional<IntermediateOperationReference> optionalNext = operationReference.next();

            if (optionalNext.isPresent()) {
                final IntermediateOperationReference nextReference = optionalNext.get();
                final IntermediateOperation<?, ?> next = nextReference.get();

                if (next.type() == LIMIT) {
                    query.setMaxResults((int) getArgument(next));
                    mergingTracker.markAsMerged(next.type());
                    mergingTracker.markForRemoval(nextReference.index());
                }
            } else {
                query.setMaxResults(Integer.MAX_VALUE);
            }
        } else {
            query.setMaxResults((int) getArgument(operation));
            mergingTracker.markAsMerged(operationType);
            mergingTracker.markForRemoval(operationReference.index());
        }
    }

    private long getArgument(final IntermediateOperation<?, ?> intermediateOperation) {
        final Object[] arguments = intermediateOperation.arguments();

        if (arguments.length != 1) {
            return 0;
        }

        if (arguments[0] instanceof Long) {
            return (long) arguments[0];
        }

        return 0;
    }
}
