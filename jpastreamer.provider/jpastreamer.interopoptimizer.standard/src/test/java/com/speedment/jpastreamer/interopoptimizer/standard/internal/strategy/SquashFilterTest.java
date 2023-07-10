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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.model.Film;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.model.Film$;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashFilterTest extends SquashTest<Film, SquashFilter<?>> {

    @Override
    SquashFilter<?> getSquashInstance() {
        return new SquashFilter<>(operationFactory);
    }

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
            noFilter(),
            filterN(1), filterN(2), filterN(3), filterN(10), filterN(100),
            filterOther(),
            filter2Other2(),
            filter2OtherFilter2(), 
            filterAndLambdaFilter2()
        );
    }

    private PipelineTestCase<Film> noFilter() {
        final Pipeline<Film> noFilter = createPipeline(
            operationFactory.createSkip(1)
        );

        final Pipeline<Film> noFilterExpected = createPipeline(
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("No Filter", noFilter, noFilterExpected);
    }

    private PipelineTestCase<Film> filterN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.range(0, n).mapToObj(i -> operationFactory.createFilter(Film$.title.startsWith("A"))).toArray(IntermediateOperation[]::new);

        final Pipeline<Film> filter = createPipeline(operations);

        final Pipeline<Film> filterExpected = createPipeline(operationFactory.createFilter(Film$.title.startsWith("A")));

        return new PipelineTestCase<>("Filter " + n, filter, filterExpected);
    }

    private PipelineTestCase<Film> filterOther() {
        final Pipeline<Film> filterOther = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("A")),
            operationFactory.createSkip(1)
        );

        final Pipeline<Film> filterOtherExpected = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("A")),
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Filter, Other", filterOther, filterOtherExpected);
    }

    private PipelineTestCase<Film> filter2Other2() {
        final Pipeline<Film> filter2Other2 = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("A")), operationFactory.createFilter(Film$.title.endsWith("B")),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        final Pipeline<Film> filter2Other2Expected = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("A").and(Film$.title.endsWith("B"))),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Filter 2, Other 2", filter2Other2, filter2Other2Expected);
    }

    private PipelineTestCase<Film> filter2OtherFilter2() {
        final Pipeline<Film> filter2OtherFilter2 = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("T")), operationFactory.createFilter(Film$.title.endsWith("C")),
            operationFactory.createLimit(1),
            operationFactory.createFilter(Film$.title.startsWith("A")), operationFactory.createFilter(Film$.title.startsWith("B"))
        );

        final Pipeline<Film> filter2OtherFilter2Expected = createPipeline(
            operationFactory.createFilter(Film$.title.startsWith("T").and(Film$.title.endsWith("C"))),
            operationFactory.createLimit(1),
            operationFactory.createFilter(Film$.title.startsWith("A").and(Film$.title.endsWith("B")))
        );

        return new PipelineTestCase<>("Filter 2, Other, Filter 2", filter2OtherFilter2, filter2OtherFilter2Expected);
    }

    private PipelineTestCase<Film> filterAndLambdaFilter2() {
        final Pipeline<Film> filter2OtherFilter2 = createPipeline(
                operationFactory.createFilter(Film$.title.startsWith("T")), operationFactory.createFilter(x -> true),
                operationFactory.createLimit(1),
                operationFactory.createFilter(Film$.title.startsWith("A")), operationFactory.createFilter(Film$.title.startsWith("B"))
        );

        final Pipeline<Film> filter2OtherFilter2Expected = createPipeline(
                operationFactory.createFilter(Film$.title.startsWith("T")),
                operationFactory.createFilter(x -> true),
                operationFactory.createLimit(1),
                operationFactory.createFilter(Film$.title.startsWith("A").and(Film$.title.endsWith("B")))
        );

        return new PipelineTestCase<>("Filter and Lambda, Filter 2", filter2OtherFilter2, filter2OtherFilter2Expected);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void assertArguments(Object[] expected, Object[] actual) {
        if (expected[0] instanceof Predicate && actual[0] instanceof Predicate) {
            final Predicate expectedPredicate = (Predicate) expected[0];
            final Predicate actualPredicate = (Predicate) actual[0];

            final Film film = new Film((short) 1234, "TITANIC", "About a ship that sank.");
            assertEquals(expectedPredicate.test(film), actualPredicate.test(film));
        } else {
            super.assertArguments(expected, actual);
        }
    }
}
