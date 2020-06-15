package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.InternalPipelineFactory;

public final class StandardPipelineFactory implements PipelineFactory {

    private final PipelineFactory delegate;

    public StandardPipelineFactory() {
        this.delegate = new InternalPipelineFactory();
    }

    @Override
    public <T> Pipeline<T> createPipeline(final Class<T> rootClass) {
        return delegate.createPipeline(rootClass);
    }

}