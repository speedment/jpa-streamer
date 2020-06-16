package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.renderer.RendererFactory;

import static java.util.Objects.requireNonNull;

final class StandardFactories implements Factories {

    private final PipelineFactory pipeline;
    private final IntermediateOperationFactory intermediate;
    private final TerminalOperationFactory terminal;
    private final AutoCloseFactory autoClose;

    public StandardFactories(final PipelineFactory pipeline,
                             final IntermediateOperationFactory intermediate,
                             final TerminalOperationFactory terminal,
                             final AutoCloseFactory autoClose) {
        this.pipeline = requireNonNull(pipeline);
        this.intermediate = requireNonNull(intermediate);
        this.terminal = requireNonNull(terminal);
        this.autoClose = requireNonNull(autoClose);
    }

    @Override
    public PipelineFactory pipeline() {
        return pipeline;
    }

    @Override
    public IntermediateOperationFactory intermediate() {
        return intermediate;
    }

    @Override
    public TerminalOperationFactory terminal() {
        return terminal;
    }

    @Override
    public AutoCloseFactory autoClose() {
        return autoClose;
    }

}