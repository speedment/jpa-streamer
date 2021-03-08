/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.DoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.LongIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.DoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.IntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.LongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ServiceLoader;

enum InjectedFactories implements Factories {
    INSTANCE;

    private final PipelineFactory pipeline;
    private final IntermediateOperationFactory intermediate;
    private final IntIntermediateOperationFactory intIntermediate;
    private final LongIntermediateOperationFactory longIntermediate;
    private final DoubleIntermediateOperationFactory doubleIntermediate;
    private final TerminalOperationFactory terminal;
    private final IntTerminalOperationFactory intTerminal;
    private final LongTerminalOperationFactory longTerminal;
    private final DoubleTerminalOperationFactory doubleTerminal;
    // private final AutoCloseFactory autoClose;

    InjectedFactories() {
        pipeline = RootFactory.getOrThrow(PipelineFactory.class, ServiceLoader::load);
        intermediate = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
        intIntermediate = RootFactory.getOrThrow(IntIntermediateOperationFactory.class, ServiceLoader::load);
        longIntermediate = RootFactory.getOrThrow(LongIntermediateOperationFactory.class, ServiceLoader::load);
        doubleIntermediate = RootFactory.getOrThrow(DoubleIntermediateOperationFactory.class, ServiceLoader::load);

        terminal = RootFactory.getOrThrow(TerminalOperationFactory.class, ServiceLoader::load);
        intTerminal = RootFactory.getOrThrow(IntTerminalOperationFactory.class, ServiceLoader::load);
        longTerminal = RootFactory.getOrThrow(LongTerminalOperationFactory.class, ServiceLoader::load);
        doubleTerminal = RootFactory.getOrThrow(DoubleTerminalOperationFactory.class, ServiceLoader::load);
        //autoClose = RootFactory.getOrThrow(AutoCloseFactory.class, ServiceLoader::load);
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
    public IntIntermediateOperationFactory intIntermediate() {
        return intIntermediate;
    }

    @Override
    public LongIntermediateOperationFactory longIntermediate() {
        return longIntermediate;
    }

    @Override
    public DoubleIntermediateOperationFactory doubleIntermediate() {
        return doubleIntermediate;
    }

    @Override
    public TerminalOperationFactory terminal() {
        return terminal;
    }

    @Override
    public IntTerminalOperationFactory intTerminal() {
        return intTerminal;
    }

    @Override
    public LongTerminalOperationFactory longTerminal() {
        return longTerminal;
    }

    @Override
    public DoubleTerminalOperationFactory doubleTerminal() {
        return doubleTerminal;
    }

    /*@Override
    public AutoCloseFactory autoClose() {
        return autoClose;
    }*/

}
