package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizer;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.LinkedList;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.*;

public class MoveAnonymousLambdaOperations implements IntermediateOperationOptimizer {
    
    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {

        LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();
        LinkedList<IntermediateOperation<?, ?>> optimizedOperations = new LinkedList<>(); 
        for (int i = 0; i < intermediateOperations.size(); i++) {
            final IntermediateOperation<?, ?> intermediateOperation = intermediateOperations.get(i); 
            if (i == 0 
                    || !movable(intermediateOperation)) {
                optimizedOperations.add(intermediateOperation); 
                continue; 
            }
            int position = optimizedOperations.size(); // default placement - end of list 
            for(int j = optimizedOperations.size() - 1; j >= 0; j--) {
                final IntermediateOperation<?, ?> currentOperation = optimizedOperations.get((j)); 
                if(shouldSwap(intermediateOperation, currentOperation)) {
                    position = j; 
                } else {
                    break; 
                }
            }
            if (position < optimizedOperations.size()) {
                optimizedOperations.add(position, intermediateOperation);
            } else {
                optimizedOperations.add(intermediateOperation); 
            }
        }
        
        // Update pipeline 
        for (int k = 0; k < intermediateOperations.size(); k++) {
            final IntermediateOperation<?, ?> intermediateOperation = optimizedOperations.get(k);
            intermediateOperations.set(k, intermediateOperation);
        }
        
        return pipeline;
    }
    
    private boolean movable(final IntermediateOperation<?, ?> operation) {
        final IntermediateOperationType type = operation.type();
        return (type == FILTER || type == SORTED || type == DISTINCT) && jpaStreamerOperator(operation); 
    }

    private boolean shouldSwap(final IntermediateOperation<?, ?> operation, final IntermediateOperation<?, ?> nextOperation) {
        final IntermediateOperationType type = operation.type();
        final IntermediateOperationType nextType = nextOperation.type();
        final boolean jpaStreamerOperation = jpaStreamerOperator(nextOperation);
        switch (type) {
            case FILTER:
                if (nextType == SORTED || nextType == DISTINCT) {
                    return true; 
                } else if (nextType == FILTER) {
                    return !jpaStreamerOperation;
                } 
                return false; 
            case SORTED:
                return (nextType == FILTER || nextType == DISTINCT) && !jpaStreamerOperation;
            case DISTINCT:
                return (nextType == FILTER || nextType == SORTED) && !jpaStreamerOperation;
            default:
                return false;
        }
    }
    
    private boolean jpaStreamerOperator(final IntermediateOperation<?, ?> operation) {
        if (operation.type() == DISTINCT){
            return true;
        }
        final Object[] arguments = operation.arguments();
        return (arguments != null && (arguments[0] instanceof SpeedmentPredicate || arguments[0] instanceof Field));
    }
    
}
