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

package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import static com.speedment.jpastreamer.pipeline.intermediate.Statement.MODIFIES_ORDER;
import static com.speedment.jpastreamer.pipeline.intermediate.Statement.MODIFIES_SORTED;
import static com.speedment.jpastreamer.pipeline.intermediate.Verb.MODIFIES;
import static com.speedment.jpastreamer.pipeline.terminal.OrderPreservation.NOT_REQUIRED;
import static com.speedment.jpastreamer.pipeline.terminal.OrderPreservation.NOT_REQUIRED_IF_PARALLEL;

import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizer;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.terminal.OrderPreservation;

import java.util.LinkedList;

public final class RemoveOrderAffectingOperations implements IntermediateOperationOptimizer {

    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {
        final OrderPreservation termOpOrderPreservation = pipeline.terminatingOperation().type().orderPreservation();

        if (pipeline.isUnordered() || termOpOrderPreservation == NOT_REQUIRED ||
            (pipeline.isParallel() && termOpOrderPreservation == NOT_REQUIRED_IF_PARALLEL)) {

            final LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

            for (int i = intermediateOperations.size() - 1; i >= 0; i--) {
                final IntermediateOperationType intermediateOperationType = intermediateOperations.get(i).type();

                final boolean canBeRemoved = intermediateOperationType.statements().stream()
                    .filter(statement -> statement.verb() == MODIFIES)
                    .allMatch(statement -> statement == MODIFIES_ORDER || statement == MODIFIES_SORTED);

                if (!canBeRemoved) {
                    break;
                }

                intermediateOperations.remove(i);
            }
        }

        return pipeline;
    }

}
