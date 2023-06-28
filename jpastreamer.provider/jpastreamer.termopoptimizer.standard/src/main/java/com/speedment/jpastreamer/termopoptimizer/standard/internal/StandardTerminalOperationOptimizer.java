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
package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.terminal.OrderPreservation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizer;

import java.nio.channels.Pipe;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

import static com.speedment.jpastreamer.pipeline.intermediate.Statement.PRESERVES_SIZE;
import static java.util.Objects.requireNonNull;

final class StandardTerminalOperationOptimizer implements TerminalOperationOptimizer {
    
    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {
        requireNonNull(pipeline);
        
        if (pipeline.terminatingOperation().type().equals(TerminalOperationType.COUNT)) {
            return countOptimization(pipeline); 
        }
        return pipeline;
    }

    private <T> Pipeline<T> countOptimization(Pipeline<T> pipeline) {
        // Starting from the end, the longest string of Intermediate operators that preserve the 
        // size property can safely be removed.
        final LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();
        
        for (int i = intermediateOperations.size() - 1; i >= 0; i--) {
            final IntermediateOperationType intermediateOperationType = intermediateOperations.get(i).type();
            if (intermediateOperationType.statements().contains(PRESERVES_SIZE)) {
                intermediateOperations.remove(i); 
            } else {
                break; 
            }
        }
        
        pipeline.ordered(false);
        return pipeline;
    }
    
}
