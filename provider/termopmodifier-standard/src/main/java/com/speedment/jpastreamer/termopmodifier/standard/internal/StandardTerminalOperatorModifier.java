package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import static java.util.Objects.requireNonNull;

final class StandardTerminalOperatorModifier implements com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier {

    @Override
    public <T> Pipeline<T> modify(Pipeline<T> pipeline) {
        requireNonNull(pipeline);
        // For now, just return whatever we get.
        return pipeline;
    }
}