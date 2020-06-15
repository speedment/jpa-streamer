package com.speedment.jpastreamer.preoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.preoptimizer.PreOptimizer;

import java.util.Objects;

final class RemovePeek implements PreOptimizer {

    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {

        // Todo: Implement optimizer
        pipeline.intermediateOperations()
                .removeIf(io -> io.type() == IntermediateOperationType.PEEK);

        return pipeline;
    }

}