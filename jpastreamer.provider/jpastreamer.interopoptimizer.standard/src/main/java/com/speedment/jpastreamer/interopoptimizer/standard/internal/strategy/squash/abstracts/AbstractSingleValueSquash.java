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
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.abstracts;

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.SingleValueSquash;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.LinkedList;
import java.util.Optional;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.FILTER;

public abstract class AbstractSingleValueSquash<S> implements SingleValueSquash<S> {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Pipeline<T> optimize(final Pipeline<T> pipeline) {
        S result = initialValue();

        final LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

        for (int i = intermediateOperations.size() - 1; i >= 0; i--) {
            final IntermediateOperation<?, ?> intermediateOperation = intermediateOperations.get(i);

            if (intermediateOperation.type() == operationType()) {
                if (intermediateOperation.arguments().length == 0) {
                    if (result != checkValue()) {
                        final IntermediateOperation<?, ?> newOperation = operationProvider()
                                .apply(result);
                        intermediateOperations.add(i + 1, newOperation);

                        result = resetValue();
                    }
                    continue;
                }

                if (valueClass().isAssignableFrom(intermediateOperation.arguments()[0].getClass())) {
                    S value = (S) intermediateOperation.arguments()[0];
                    if (operationType() != FILTER || value instanceof SpeedmentPredicate) {
                        result = squash().apply(value, result);

                        intermediateOperations.remove(i);
                    }
                }

                continue;
            }

            if (result != checkValue()) {
                final IntermediateOperation<?, ?> newOperation = operationProvider().apply(result);
                intermediateOperations.add(i + 1, newOperation);

                result = resetValue();
            }
        }

        if (result != checkValue()) {
            final IntermediateOperation<?, ?> newOperation = operationProvider().apply(result);
            intermediateOperations.addFirst(newOperation);
        }

        return pipeline;
    }
    
}
