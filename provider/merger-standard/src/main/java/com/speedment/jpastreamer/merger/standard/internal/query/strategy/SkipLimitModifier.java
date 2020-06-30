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
