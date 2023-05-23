package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import java.util.function.Predicate;
import java.util.stream.Stream;

final class AnyMatchTest extends StandardTerminalOperationOptimizerTest<String> {

    @Override
    Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    protected Stream<PipelineTestCase<String>> pipelines() {
        return Stream.of(
                noAnyMatch(),
                anyMatch()
        );
    }
    
    private PipelineTestCase<String> noAnyMatch() {
        final Pipeline<String> noAnyMatch = createPipeline(
                tof.createAllMatch(s -> s.equals("test")),
                iof.createLimit(1)
        );

        final Pipeline<String> noAnyMatchExpected = createPipeline(
                tof.createAllMatch(s -> s.equals("test")),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("No Any Match", noAnyMatch, noAnyMatchExpected);
    }

    private PipelineTestCase<String> anyMatch() {
        final Predicate<String> p = s -> (s.equals("test"));

        final Pipeline<String> anyMatch = createPipeline(
                tof.createAnyMatch(p),
                iof.createLimit(100)
        );

        final Pipeline<String> anyMatchExpected = createPipeline(
                tof.createAnyMatch(s -> true),
                iof.createLimit(100),
                iof.createLimit(1),
                iof.createFilter(p)
        );

        return new PipelineTestCase<>("Any Match", anyMatch, anyMatchExpected);
    }
    
}
