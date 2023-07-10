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
package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopoptimizer.standard.internal.model.Film;
import com.speedment.jpastreamer.termopoptimizer.standard.internal.model.Film$;

import java.util.stream.Stream;

public class CountTest extends StandardTerminalOperationOptimizerTest {
    
    @Override
    Class getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase> pipelines() {
        return Stream.of(
                noCountTest(), 
                countTest1(),
                countTest2(),
                countTest3(),
                countTest4(),
                countTest5()
        );
    }

    private PipelineTestCase<Film> noCountTest() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireFindFirst(),
                iof.createLimit(100),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title)
        );

        final Pipeline<Film> optimized = createPipeline(
                tof.acquireFindFirst(),
                iof.createLimit(100),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title)
        );

        return new PipelineTestCase<>("No Count Test ", unoptimized, optimized);
    }
    

    private PipelineTestCase<Film> countTest1() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title)
        );

        final Pipeline<Film> optimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("Count Test 1", unoptimized, optimized);
    }

    private PipelineTestCase<Film> countTest2() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100)
        );
        final Pipeline<Film> optimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("Count Test 2", unoptimized, optimized);
    }

    private PipelineTestCase<Film> countTest3() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createSorted(Film$.length),
                iof.createFilter(Film$.title.equal("TITANIC"))
        );

        final Pipeline<Film> optimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createSorted(Film$.length),
                iof.createFilter(Film$.title.equal("TITANIC"))
        );

        return new PipelineTestCase<>("Count Test 3", unoptimized, optimized);
    }

    private PipelineTestCase<Film> countTest4() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createFilter(Film$.title.equal("TITANIC")),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title),
                iof.createMap(Film$.length.asInt())
        );

        final Pipeline<Film> optimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createFilter(Film$.title.equal("TITANIC"))
        );

        return new PipelineTestCase<>("Count Test 4", unoptimized, optimized);
    }

    private PipelineTestCase<Film> countTest5() {
        final Pipeline<Film> unoptimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createFilter(Film$.title.equal("TITANIC")),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title),
                iof.createFilter(Film$.length.greaterThan((short) 60)),
                iof.createMap(Film$.length.asInt())
        );

        final Pipeline<Film> optimized = createPipeline(
                tof.acquireCount(),
                iof.createLimit(100),
                iof.createFilter(Film$.title.equal("TITANIC")),
                iof.createSorted(Film$.length),
                iof.createSorted(Film$.title),
                iof.createFilter(Film$.length.greaterThan((short) 60))
        );

        return new PipelineTestCase<>("Count Test 5", unoptimized, optimized);
    }
    
}
