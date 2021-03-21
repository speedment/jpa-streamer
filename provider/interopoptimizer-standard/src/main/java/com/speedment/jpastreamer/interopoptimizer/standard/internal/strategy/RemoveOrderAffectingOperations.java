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
import com.speedment.jpastreamer.pipeline.intermediate.Statement;
import com.speedment.jpastreamer.pipeline.terminal.OrderPreservation;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class RemoveOrderAffectingOperations implements IntermediateOperationOptimizer {

    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {
        final OrderPreservation termOpOrderPreservation = pipeline.terminatingOperation().type().orderPreservation();

        if (pipeline.isUnordered() || termOpOrderPreservation == NOT_REQUIRED ||
            (pipeline.isParallel() && termOpOrderPreservation == NOT_REQUIRED_IF_PARALLEL)) {

            final LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

            for (int i = intermediateOperations.size() - 1; i >= 0; i--) {
                final IntermediateOperationType intermediateOperationType = intermediateOperations.get(i).type();

                final List<Statement> modifyingStatements = intermediateOperationType.statements().stream()
                    .filter(statement -> statement.verb() == MODIFIES)
                    .collect(Collectors.toList());

                if (modifyingStatements.isEmpty()) {
                    break;
                }

                if (!modifyingStatements.stream()
                    .allMatch(statement -> statement == MODIFIES_ORDER || statement == MODIFIES_SORTED)) {
                    break;
                }

                intermediateOperations.remove(i);
            }
        }

        return pipeline;
    }

}
