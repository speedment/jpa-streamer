package com.speedment.jpastreamer.termopmodifier.standard.internal;


import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film$;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class NoneMatchTest extends StandardTerminalOperationModifierTest<Film>{

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                noNoneMatch(),
                noneMatchLambda(),
                noneMatchSpeedmentPredicate()
        );
    }

    private PipelineTestCase<Film> noNoneMatch() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> noAnyMatch = createPipeline(
                tof.createAllMatch(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> noAnyMatchExpected = createPipeline(
                tof.createAllMatch(p),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("No None Match", noAnyMatch, noAnyMatchExpected);
    }

    private PipelineTestCase<Film> noneMatchLambda() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> anyMatch = createPipeline(
                tof.createNoneMatch(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> anyMatchExpected = createPipeline(
                tof.createNoneMatch(p),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("None Match Lambda", anyMatch, anyMatchExpected);
    }

    private PipelineTestCase<Film> noneMatchSpeedmentPredicate() {
        SpeedmentPredicate<Film> p = Film$.title.startsWith("A");

        final Pipeline<Film> anyMatch = createPipeline(
                tof.createNoneMatch(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> anyMatchExpected = createPipeline(
                tof.createNoneMatch(s -> true),
                iof.createLimit(100),
                iof.createFilter(p),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("None Match Speedment Predicate", anyMatch, anyMatchExpected);
    }

}
