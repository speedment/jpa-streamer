package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film$;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindAnyTest extends StandardTerminalOperationModifierTest<Film> {
    
    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                noFindAny(),
                findAny()
        );
    }

    private PipelineTestCase<Film> noFindAny() {
        final Pipeline<Film> noFindAny = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        final Pipeline<Film> noFindAnyExpected = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("No Find Any", noFindAny, noFindAnyExpected);
    }

    private PipelineTestCase<Film> findAny() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> findAny = createPipeline(
                tof.acquireFindAny(),
                iof.createFilter(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> findAnyExpected = createPipeline(
                tof.acquireFindAny(),
                iof.createFilter(p),
                iof.createLimit(100),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("Find Any", findAny, findAnyExpected);
    }
}
