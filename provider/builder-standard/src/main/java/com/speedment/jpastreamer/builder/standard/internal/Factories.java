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

interface Factories {

    PipelineFactory pipeline();

    IntermediateOperationFactory intermediate();

    IntIntermediateOperationFactory intIntermediate();

    LongIntermediateOperationFactory longIntermediate();

    DoubleIntermediateOperationFactory doubleIntermediate();

    TerminalOperationFactory terminal();

    IntTerminalOperationFactory intTerminal();

    LongTerminalOperationFactory longTerminal();

    DoubleTerminalOperationFactory doubleTerminal();

    //RendererFactory renderer();

    // AutoCloseFactory autoClose();

}
