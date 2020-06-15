package com.speedment.jpastreamer.preoptimizer;

import com.speedment.jpastreamer.pipeline.Pipeline;

@FunctionalInterface
public interface PreOptimizer {

    <T> Pipeline<T> optimize(Pipeline<T> pipeline);

}