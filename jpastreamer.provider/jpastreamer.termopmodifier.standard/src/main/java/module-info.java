import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifierFactory;
import com.speedment.jpastreamer.termopmodifier.standard.StandardTerminalOperatorModifierFactory;

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
module jpastreamer.termopmodifier.standard {
    requires transitive jpastreamer.termopmodifier;
    requires jpastreamer.rootfactory;
    requires jpastreamer.pipeline;
    requires jpastreamer.field;

    exports com.speedment.jpastreamer.termopmodifier.standard;
    
    uses PipelineFactory;
    uses TerminalOperationFactory; 
    uses IntermediateOperationFactory; 
    
    provides TerminalOperationModifierFactory with StandardTerminalOperatorModifierFactory;
}
