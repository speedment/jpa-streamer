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

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashSortedTest extends SquashTest<Film, SquashSorted<?>> {

    @Override
    SquashSorted<?> getSquashInstance() {
        return new SquashSorted<>(operationFactory);
    }

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
            noSorted(),
            sortedN(1), sortedN(2), sortedN(3), sortedN(10), sortedN(100),
            sortedOther(),
            sorted2Other2(),
            sorted2OtherSorted2()
        );
    }

    private PipelineTestCase<Film> noSorted() {
        final Pipeline<Film> noSorted = createPipeline(
            operationFactory.createSkip(1)
        );

        final Pipeline<Film> noSortedExpected = createPipeline(
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("No Sorted", noSorted, noSortedExpected);
    }

    private PipelineTestCase<Film> sortedN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.range(0, n).mapToObj(i -> operationFactory.createSorted(Film$.length)).toArray(IntermediateOperation[]::new);

        final Pipeline<Film> sorted = createPipeline(operations);

        final Pipeline<Film> sortedExpected = createPipeline(operationFactory.createSorted(Film$.length));

        return new PipelineTestCase<>("Sorted " + n, sorted, sortedExpected);
    }

    private PipelineTestCase<Film> sortedOther() {
        final Pipeline<Film> sortedOther = createPipeline(
            operationFactory.createSorted(Film$.length),
            operationFactory.createSkip(1)
        );

        final Pipeline<Film> sortedOtherExpected = createPipeline(
            operationFactory.createSorted(Film$.length),
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Sorted, Other", sortedOther, sortedOtherExpected);
    }

    private PipelineTestCase<Film> sorted2Other2() {
        final Comparator<Film> first = Film$.length;
        final Comparator<Film> second = Film$.title;

        final Pipeline<Film> sorted2Other2 = createPipeline(
            operationFactory.createSorted(first), operationFactory.createSorted(second),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        final Pipeline<Film> sorted2Other2Expected = createPipeline(
            operationFactory.createSorted(first.thenComparing(second)),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Sorted 2, Other 2", sorted2Other2, sorted2Other2Expected);
    }

    private PipelineTestCase<Film> sorted2OtherSorted2() {
        final Comparator<Film> first = Film$.length;
        final Comparator<Film> second = Film$.title;

        final Pipeline<Film> sorted2OtherSorted2 = createPipeline(
            operationFactory.createSorted(first), operationFactory.createSorted(second),
            operationFactory.createLimit(1),
            operationFactory.createSorted(second), operationFactory.createSorted(first)
        );


        final Pipeline<Film> sorted2OtherSorted2Expected = createPipeline(
            operationFactory.createSorted(first.thenComparing(second)),
            operationFactory.createLimit(1),
            operationFactory.createSorted(second.thenComparing(first))
        );

        return new PipelineTestCase<>("Sorted 2, Other, Sorted 2", sorted2OtherSorted2, sorted2OtherSorted2Expected);
    }

    private PipelineTestCase<Film> sortedLambdaOtherLambdaSorted() {
        final Comparator<Film> first = Film$.length;
        final Comparator<Film> second = Comparator.comparing(Film::getTitle); 
        
        final Pipeline<Film> sorted2OtherSorted2 = createPipeline(
                operationFactory.createSorted(first), operationFactory.createSorted(second),
                operationFactory.createLimit(1),
                operationFactory.createSorted(second), operationFactory.createSorted(first)
        );


        final Pipeline<Film> sorted2OtherSorted2Expected = createPipeline(
                operationFactory.createSorted(first), operationFactory.createSorted(second),
                operationFactory.createLimit(1),
                operationFactory.createSorted(second), operationFactory.createSorted(first)
        );

        return new PipelineTestCase<>("Sorted, Lambda, Other, Lambda, Sorted", sorted2OtherSorted2, sorted2OtherSorted2Expected);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void assertArguments(Object[] expected, Object[] actual) {
        if (expected[0] instanceof Comparator && actual[0] instanceof Comparator) {
            final Comparator expectedPredicate = (Comparator) expected[0];
            final Comparator actualPredicate = (Comparator) actual[0];

            final Film film1 = new Film((short) 1, "Titanic", "A film about a boat.");
            final Film film2 = new Film((short) 2, "Star Wars", "A film about war in space.");
            
            assertEquals(expectedPredicate.compare(film1, film2), actualPredicate.compare(film1, film2));
        } else {
            super.assertArguments(expected, actual);
        }
    }
}
