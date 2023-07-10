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
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.model.Film;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.model.Film$;
import com.speedment.jpastreamer.pipeline.Pipeline;

import java.util.Comparator;
import java.util.stream.Stream;

public class MoveAnonymousLambdaOperationsTest extends InternalOptimizerTest<Film> {
    
    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                test1(),
                test2(),
                test3(),
                test4(),
                test5(),
                test6(),
                test7(), 
                test8(),
                test9(),
                test10(),
                test11()
        );
    }

    private PipelineTestCase<Film> test1() {
        // No optimizations can be made 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createLimit(1)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("Reorder Test 1", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test2() {
        // Pipeline can be fully optimized 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(f -> f.equals("")),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.length),
                iof.createFilter(f -> f.equals(""))
        );

        return new PipelineTestCase<>("Reorder Test 2", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test3() {
        // Pipeline can be fully optimized 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(f -> f.equals("a")),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createFilter(f -> f.equals("b")),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.length),
                iof.createFilter(f -> f.equals("b")),
                iof.createFilter(f -> f.equals("a"))
        );

        return new PipelineTestCase<>("Reorder Test 3", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test4() {
        // The map blocks the anonymous filter from being moved up the pipeline 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(f -> f.equals("a")),
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createFilter(f -> f.equals("b")),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(f -> f.equals("a")),
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.length),
                iof.createFilter(f -> f.equals("b"))
        );

        return new PipelineTestCase<>("Reorder Test 4", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test5() {
        // Anonymous lambdas are moved to the end of the pipeline
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(f -> f.equals("a")),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createFilter(f -> f.equals("b")),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.length),
                iof.createFilter(f -> f.equals("b")),
                iof.createFilter(f -> f.equals("a"))
                );

        return new PipelineTestCase<>("Reorder Test 5", unoptimized, optimized);
    }


    private PipelineTestCase<Film> test6() {
        // Unchanged as sorts cannot be rearranged
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Film$.length)
        );

        return new PipelineTestCase<>("Reorder Test 6", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test7() {
        // The sorts cannot be reordered even though the first sort uses an anonymous lambda 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(f -> f.equals("b")),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.acquireDistinct(),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Film$.length)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.acquireDistinct(),
                iof.createFilter(f -> f.equals("b")),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Film$.length)
        );

        return new PipelineTestCase<>("Reorder Test 7", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test8() {
        // The two sorts cannot be reordered, but both operations can be moved past the distinct() and filter() 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.acquireDistinct(),
                iof.createFilter(Film$.length.greaterThan((short) 120))
        );

        final Pipeline<Film> optimized = createPipeline(                
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.acquireDistinct(),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createSorted(Comparator.comparing(Object::toString))
        );

        return new PipelineTestCase<>("Reorder Test 8", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test9() {
        // No operations can be moved past the map 
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.acquireDistinct(),
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createLimit(100),
                iof.createSkip(10)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.acquireDistinct(),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createMap(Film$.length.asInt()),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createLimit(100),
                iof.createSkip(10)
        );

        return new PipelineTestCase<>("Reorder Test 9", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test10() {
        // No operations can be moved past the limit
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.acquireDistinct(),
                iof.createLimit(100),
                iof.createFilter(Film$.length.greaterThan((short) 120))
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.acquireDistinct(),
                iof.createSorted(Comparator.comparing(Object::toString)),
                iof.createLimit(100),
                iof.createFilter(Film$.length.greaterThan((short) 120))
        );

        return new PipelineTestCase<>("Reorder Test 10", unoptimized, optimized);
    }

    private PipelineTestCase<Film> test11() {
        final Pipeline<Film> unoptimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createFilter(Film$.title.startsWith("A")),
                iof.acquireDistinct(),
                iof.createSorted(Film$.length),
                iof.createFilter(Film$.rating.equal("PG-13")),
                iof.createSkip(10),
                iof.createSorted(Film$.title),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createMap(Film$.title),
                iof.createLimit(40)
        );

        final Pipeline<Film> optimized = createPipeline(
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createFilter(Film$.title.startsWith("A")),
                iof.createFilter(Film$.rating.equal("PG-13")),
                iof.acquireDistinct(),
                iof.createSorted(Film$.length),
                iof.createSkip(10),
                iof.createFilter(Film$.length.greaterThan((short) 120)),
                iof.createSorted(Film$.title),
                iof.createMap(Film$.title),
                iof.createLimit(40)
        );

        return new PipelineTestCase<>("Reorder Test 11", unoptimized, optimized);
    }

}
