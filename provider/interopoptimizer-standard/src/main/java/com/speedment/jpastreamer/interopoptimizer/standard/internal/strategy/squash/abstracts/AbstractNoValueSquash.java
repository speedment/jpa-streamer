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
