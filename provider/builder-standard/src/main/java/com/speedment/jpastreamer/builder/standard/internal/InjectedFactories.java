package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ServiceLoader;

enum InjectedFactories implements Factories {
    INSTANCE;

    private final PipelineFactory pipeline;
    private final IntermediateOperationFactory intermediate;
    private final TerminalOperationFactory terminal;
    private final AutoCloseFactory autoClose;

    InjectedFactories() {
        pipeline = RootFactory.getOrThrow(PipelineFactory.class, ServiceLoader::load);
        intermediate = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
        terminal = RootFactory.getOrThrow(TerminalOperationFactory.class, ServiceLoader::load);
        autoClose = RootFactory.getOrThrow(AutoCloseFactory.class, ServiceLoader::load);
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