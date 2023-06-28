import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizerFactory;
import com.speedment.jpastreamer.termopoptimizer.standard.StandardTerminalOperationOptimizerFactory;

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
module jpastreamer.termopoptimizer.standard {
    requires transitive jpastreamer.termopoptimizer;
    requires jpastreamer.pipeline;
    
    exports com.speedment.jpastreamer.termopoptimizer.standard;

    uses PipelineFactory;
    uses TerminalOperationFactory;
    uses IntermediateOperationFactory;

    provides TerminalOperationOptimizerFactory with StandardTerminalOperationOptimizerFactory;
}
