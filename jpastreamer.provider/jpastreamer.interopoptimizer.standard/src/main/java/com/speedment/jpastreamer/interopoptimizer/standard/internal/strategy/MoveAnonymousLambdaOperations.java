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

    public <T> Pipeline<T> optimize2(Pipeline<T> pipeline) {

        LinkedList<IntermediateOperation<?, ?>> intermediateOperations = pipeline.intermediateOperations();

        int i = 0;
        while(i < intermediateOperations.size() - 1) {
            final IntermediateOperation<?, ?> intermediateOperation = intermediateOperations.get(i);
            final IntermediateOperationType iot = intermediateOperation.type();
            if (movable(iot) && !anonymousLambda(intermediateOperation) ) {
                // We only move movable operations with anonymous lambdas
                int j = i + 1;
                int currentPos = i;
                while (j < intermediateOperations.size()) {
                    final IntermediateOperation<?, ?> next = intermediateOperations.get(j);
                    final IntermediateOperationType iotNext = next.type();
                    if (swappable(iot, iotNext)) { // check if current lambda can be swapped with next operation 
                        if (iotNext == DISTINCT || anonymousLambda(next)) {
                            i = -1;
                            currentPos = swapOperations(intermediateOperations, currentPos, j);
                        } else {
                            j++;
                            i++;
                            continue;
                        }
                    } else {
                        break;
                    }
                    j++;
                }
            }
            i++;
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
    
    private int swapOperations(final LinkedList<IntermediateOperation<?, ?>> intermediateOperations,
                            final int index1, final int index2)
    {
        if (0 <= index1 && index1 < intermediateOperations.size() && 0 <= index2 && index2 < intermediateOperations.size()) {
            final IntermediateOperation<?, ?> io1 = intermediateOperations.get(index1);
            final IntermediateOperation<?, ?> io2 = intermediateOperations.get(index2);

            intermediateOperations.set(index1, io2);
            intermediateOperations.set(index2, io1);
            
            return index2; 
        }
        return -1; 
    }

    private boolean anonymousLambda(final IntermediateOperation<?, ?> operation) {
        if (operation.type() == DISTINCT){
            return true;
        }
        final Object[] arguments = operation.arguments();
        return !(arguments != null && (arguments[0] instanceof SpeedmentPredicate || arguments[0] instanceof Field));
    }
    
}
