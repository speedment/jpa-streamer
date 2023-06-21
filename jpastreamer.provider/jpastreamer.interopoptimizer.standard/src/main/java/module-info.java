import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.interopoptimizer.standard.StandardIntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;

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
module jpastreamer.interopoptimizer.standard {
    requires transitive jpastreamer.interopoptimizer;
    requires jpastreamer.rootfactory;

    exports com.speedment.jpastreamer.interopoptimizer.standard;
    
    uses IntermediateOperationFactory;
    uses PipelineFactory;
    
    provides IntermediateOperationOptimizerFactory with StandardIntermediateOperationOptimizerFactory;
}
