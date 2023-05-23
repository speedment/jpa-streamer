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

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizer;

import java.util.ServiceLoader;

final class StandardTerminalOperationOptimizer implements TerminalOperationOptimizer {
    
    final IntermediateOperationFactory iof = RootFactory
            .getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
    final TerminalOperationFactory tof = 
            RootFactory.getOrThrow(TerminalOperationFactory.class, ServiceLoader::load); 
    
    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {
        requireNonNull(pipeline);

        final TerminalOperationType terminalOperationType = pipeline.terminatingOperation().type();

        switch (terminalOperationType) {
            case ANY_MATCH: 
                return optimizeAnyMatch(pipeline);  
            case NONE_MATCH:
                return optimizeNoneMatch(pipeline); 
            case FIND_FIRST:
                return optimizeFindFirst(pipeline); 
            case FIND_ANY:
                return optimizeFindAny(pipeline); 
            default:
                return pipeline; 
        }
        
    }
    
    private <T> Pipeline<T> optimizeAnyMatch(Pipeline<T> pipeline) {
        pipeline.intermediateOperations().add(iof.createLimit(1));
        pipeline.intermediateOperations().add(
                iof.createFilter(pipeline.terminatingOperation().predicate()));
        pipeline.terminatingOperation(tof.createAnyMatch(p -> true));
        return pipeline; 
    }

    private <T> Pipeline<T> optimizeNoneMatch(Pipeline<T> pipeline) {
        pipeline.intermediateOperations().add(iof.createLimit(1)); 
        pipeline.intermediateOperations().add(
                iof.createFilter(pipeline.terminatingOperation().predicate())); 
        // NoneMatch() - If the stream is empty then true is returned and the predicate is not evaluated. 
        // If the expression is evaluated => There is a match and the expression is always false. 
        pipeline.terminatingOperation(tof.createNoneMatch(e -> false));
        return pipeline;
    }

    private <T> Pipeline<T> optimizeFindFirst(Pipeline<T> pipeline) {
        pipeline.intermediateOperations().add(iof.createLimit(1));
        return pipeline;
    }

    private <T> Pipeline<T> optimizeFindAny(Pipeline<T> pipeline) {
        pipeline.ordered(false);
        pipeline.intermediateOperations().add(iof.createLimit(1));
        return pipeline;
    }
    
}
