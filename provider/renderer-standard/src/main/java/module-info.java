import com.speedment.jpastreamer.criteria.CriteriaFactory;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.merger.MergerFactory;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.renderer.standard.StandardRendererFactory;

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
module jpastreamer.renderer.standard {

    requires transitive jpastreamer.renderer;

    requires jpastreamer.pipeline;
    requires jpastreamer.rootfactory;
    requires jpastreamer.criteria;
    requires jpastreamer.merger;
    requires jpastreamer.interopoptimizer;
    requires jpastreamer.termopmodifier;
    
    uses CriteriaFactory;
    uses MergerFactory;
    uses IntermediateOperationOptimizerFactory; 

    provides RendererFactory with StandardRendererFactory;
}
