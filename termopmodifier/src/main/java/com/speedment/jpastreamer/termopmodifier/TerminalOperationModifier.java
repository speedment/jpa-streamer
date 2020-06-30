package com.speedment.jpastreamer.termopmodifier;

import com.speedment.jpastreamer.pipeline.Pipeline;

@FunctionalInterface
public interface TerminalOperationModifier {

    /**
     * Potentially modifies the terminal operation and the
     * pipeline itself to create a more optimized pipeline.
     *
     * @param pipeline to modify
     * @param <T> pipeline type
     * @return the modified pipeline
     */
    <T> Pipeline<T> modify(Pipeline<T> pipeline);

}