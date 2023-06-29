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
            if (i == 0 || !movable(intermediateOperation.type())) {
                optimizedOperations.add(intermediateOperation); 
                continue; 
            }
            int position = optimizedOperations.size(); // default - end of list 
            for(int j = optimizedOperations.size() - 1; j >= 0; j--) {
                final IntermediateOperation<?, ?> currentOperation = optimizedOperations.get((j)); 
                if(swappable(intermediateOperation.type(), currentOperation.type()) && anonymousLambda(currentOperation)) {
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
    
    private boolean movable(final IntermediateOperationType type) {
        return type == FILTER || type == SORTED || type == DISTINCT; 
    }

    private boolean swappable(final IntermediateOperationType type, final IntermediateOperationType nextType) {
        // Anonymous sorts and lambdas can be swapped with a distinct operator to allow the inclusion 
        // of the distinct operation in the query.
        if (type == FILTER) {
            return nextType == FILTER || nextType == SORTED || nextType == DISTINCT;
        } else if (type == SORTED) {
            return nextType == FILTER || nextType == DISTINCT;
        } else if (type == DISTINCT) {
            return nextType == FILTER || nextType == SORTED; 
        }
        return false; 
    }
    
    private boolean anonymousLambda(final IntermediateOperation<?, ?> operation) {
        if (operation.type() == DISTINCT){
            return true;
        }
        final Object[] arguments = operation.arguments();
        return !(arguments != null && (arguments[0] instanceof SpeedmentPredicate || arguments[0] instanceof Field));
    }
    
}
