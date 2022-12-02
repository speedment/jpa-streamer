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
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.abstracts;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.NoValueSquash;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.LinkedList;

public abstract class AbstractNoValueSquash implements NoValueSquash {

    @Override
    public <T> Pipeline<T> optimize(final Pipeline<T> pipeline) {
        final LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

        IntermediateOperationType previousType = null;

        for (int i = intermediateOperations.size() - 1; i >= 0; i--) {
            final IntermediateOperation<?, ?> intermediateOperation = intermediateOperations.get(i);
            final IntermediateOperationType type = intermediateOperation.type();

            if (type == operationType()) {
                intermediateOperations.remove(i);
            }

            if (type != operationType() && previousType == operationType()) {
                final IntermediateOperation<?, ?> newOperation = operationProvider().get();
                intermediateOperations.add(i + 1, newOperation);
            }

            previousType = type;
        }

        if (previousType == operationType()) {
            final IntermediateOperation<?, ?> newOperation = operationProvider().get();
            intermediateOperations.addFirst(newOperation);
        }

        return pipeline;
    }
}
