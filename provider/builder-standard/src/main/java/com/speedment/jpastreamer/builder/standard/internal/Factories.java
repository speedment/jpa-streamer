package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;

interface Factories {

    PipelineFactory pipeline();

    IntermediateOperationFactory intermediate();

    TerminalOperationFactory terminal();

    //RendererFactory renderer();

    AutoCloseFactory autoClose();

}