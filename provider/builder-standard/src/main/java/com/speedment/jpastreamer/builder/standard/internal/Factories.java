package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;

import static java.util.Objects.requireNonNull;

public final class Factories {

    private final PipelineFactory pipeline;
    private final IntermediateOperationFactory intermediate;
    private final TerminalOperationFactory terminal;

    private final AutoCloseFactory autoClose;

    public Factories(final PipelineFactory pipeline,
                     final IntermediateOperationFactory intermediate,
                     final TerminalOperationFactory terminal,
                     final AutoCloseFactory autoClose) {
        this.pipeline = requireNonNull(pipeline);
        this.intermediate = requireNonNull(intermediate);
        this.terminal = requireNonNull(terminal);
        this.autoClose = requireNonNull(autoClose);
    }


    public PipelineFactory pipeline() {
        return pipeline;
    }

    public IntermediateOperationFactory intermediate() {
        return intermediate;
    }

    public TerminalOperationFactory terminal() {
        return terminal;
    }

    public AutoCloseFactory autoClose() {
        return autoClose;
    }

}