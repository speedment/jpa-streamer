package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import static java.util.Objects.requireNonNull;

final class BaseStreamSupport {

    private final Pipeline<?> pipeline;

    public BaseStreamSupport(final Pipeline<?> pipeline) {
        this.pipeline = requireNonNull(pipeline);
    }

    public boolean isParallel() {
        return pipeline.isParallel();
    }

    public void sequential() {
        pipeline.sequential();
    }

    public void parallel() {
        pipeline.parallel();
    }

    public void unordered() {
        pipeline.ordered(false);
    }

    public void onClose(Runnable closeHandler) {
        pipeline.closeHandlers().add(closeHandler);
    }

}