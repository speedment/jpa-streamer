package com.speedment.jpastreamer.pipeline.standard.internal.pipeline;


import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;

public final class InternalPipelineFactory implements PipelineFactory {

    @Override
    public <T> Pipeline<T> createPipeline(Class<T> rootClass) {
        return new StandardPipeline<>(rootClass);
    }

}