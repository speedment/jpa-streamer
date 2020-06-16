package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.rootfactory.RootFactory;

enum Factories {
    INSTANCE;

    private final PipelineFactory pipeline;
    private final IntermediateOperationFactory intermediate;
    private final TerminalOperationFactory terminal;
    private final Renderer renderer;
    private final AutoCloseFactory autoClose;

    Factories() {
        pipeline = RootFactory.getOrThrow(PipelineFactory.class);
        intermediate = RootFactory.getOrThrow(IntermediateOperationFactory.class);
        terminal = RootFactory.getOrThrow(TerminalOperationFactory.class);
        renderer = RootFactory.getOrThrow(Renderer.class);
        autoClose = RootFactory.getOrThrow(AutoCloseFactory.class);
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

    public Renderer renderer() {
        return renderer;
    }

    public AutoCloseFactory autoClose() {
        return autoClose;
    }

}