import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.DoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.LongIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.StandardPipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.standard.StandardDoubleIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.standard.StandardIntIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.standard.StandardIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.standard.StandardLongIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.DoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.IntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.LongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.StandardDoubleTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.StandardIntTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.StandardLongTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.StandardTerminalOperationFactory;

/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
module jpastreamer.pipeline.standard {
    requires transitive jpastreamer.pipeline;

    exports com.speedment.jpastreamer.pipeline.standard;
    exports com.speedment.jpastreamer.pipeline.intermediate.standard;
    exports com.speedment.jpastreamer.pipeline.terminal.standard;

    provides PipelineFactory with StandardPipelineFactory;
    provides IntermediateOperationFactory with StandardIntermediateOperationFactory; 
    provides IntIntermediateOperationFactory with StandardIntIntermediateOperationFactory; 
    provides DoubleIntermediateOperationFactory with StandardDoubleIntermediateOperationFactory; 
    provides LongIntermediateOperationFactory with StandardLongIntermediateOperationFactory; 
    provides TerminalOperationFactory with StandardTerminalOperationFactory;
    provides IntTerminalOperationFactory with StandardIntTerminalOperationFactory;
    provides LongTerminalOperationFactory with StandardLongTerminalOperationFactory;
    provides DoubleTerminalOperationFactory with StandardDoubleTerminalOperationFactory;
}
