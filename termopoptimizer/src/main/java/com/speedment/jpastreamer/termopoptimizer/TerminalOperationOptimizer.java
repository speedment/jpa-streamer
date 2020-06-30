package com.speedment.jpastreamer.termopoptimizer;

import com.speedment.jpastreamer.pipeline.Pipeline;

@FunctionalInterface
public interface TerminalOperationOptimizer {

    /**
     * Potentially optimizes the provided {@code pipeline} depending
     * on the terminal operation.
     *
     * @param pipeline to modify
     * @param <T> pipeline type
     * @return the modified pipeline
     */
    <T> Pipeline<T> optimize(Pipeline<T> pipeline);

}