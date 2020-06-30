package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizer;

import static java.util.Objects.requireNonNull;

final class StandardTerminalOperatorOptimizer implements TerminalOperationOptimizer {

    @Override
    public <T> Pipeline<T> optimize(Pipeline<T> pipeline) {
        requireNonNull(pipeline);
        // For now, just return whatever we get.
        return pipeline;
    }

}