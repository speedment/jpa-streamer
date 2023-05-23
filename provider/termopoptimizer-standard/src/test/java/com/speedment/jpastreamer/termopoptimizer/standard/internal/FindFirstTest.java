package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindFirstTest extends StandardTerminalOperationOptimizerTest<Film> {

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                noFindFirst(),
                findFirst()
        );
    }

    private PipelineTestCase<Film> noFindFirst() {
        final Pipeline<Film> noFindFirst = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        final Pipeline<Film> noFindFirstExpected = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("No Find First", noFindFirst, noFindFirstExpected);
    }

    private PipelineTestCase<Film> findFirst() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> findFirst = createPipeline(
                tof.acquireFindFirst(),
                iof.createFilter(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> findFirstExpected = createPipeline(
                tof.acquireFindFirst(),
                iof.createFilter(p),
                iof.createLimit(100),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("Find First", findFirst, findFirstExpected);
    }
    
}
