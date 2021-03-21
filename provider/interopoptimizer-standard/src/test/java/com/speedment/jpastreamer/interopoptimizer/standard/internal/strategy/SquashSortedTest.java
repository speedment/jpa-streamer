/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashSortedTest extends SquashTest<String, SquashSorted<?>> {

    @Override
    SquashSorted<?> getSquashInstance() {
        return new SquashSorted<>(operationFactory);
    }

    @Override
    Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    protected Stream<PipelineTestCase<String>> pipelines() {
        return Stream.of(
            noSorted(),
            sortedN(1), sortedN(2), sortedN(3), sortedN(10), sortedN(100),
            sortedOther(),
            sorted2Other2(),
            sorted2OtherSorted2()
        );
    }

    private PipelineTestCase<String> noSorted() {
        final Pipeline<String> noSorted = createPipeline(
            operationFactory.createSkip(1)
        );

        final Pipeline<String> noSortedExpected = createPipeline(
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("No Sorted", noSorted, noSortedExpected);
    }

    private PipelineTestCase<String> sortedN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.range(0, n).mapToObj(i -> operationFactory.createSorted((o1, o2) -> 0)).toArray(IntermediateOperation[]::new);

        final Pipeline<String> sorted = createPipeline(operations);

        final Pipeline<String> sortedExpected = createPipeline(operationFactory.createSorted((o1, o2) -> 0));

        return new PipelineTestCase<>("Sorted " + n, sorted, sortedExpected);
    }

    private PipelineTestCase<String> sortedOther() {
        final Pipeline<String> sortedOther = createPipeline(
            operationFactory.createSorted(((o1, o2) -> 0)),
            operationFactory.createSkip(1)
        );

        final Pipeline<String> sortedOtherExpected = createPipeline(
            operationFactory.createSorted((o1, o2) -> 0),
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Sorted, Other", sortedOther, sortedOtherExpected);
    }

    private PipelineTestCase<String> sorted2Other2() {
        final Comparator<Object> first = (o1, o2) -> 0;
        final Comparator<Object> second = (o1, o2) -> 1;

        final Pipeline<String> sorted2Other2 = createPipeline(
            operationFactory.createSorted(first), operationFactory.createSorted(second),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        final Pipeline<String> sorted2Other2Expected = createPipeline(
            operationFactory.createSorted(first.thenComparing(second)),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Sorted 2, Other 2", sorted2Other2, sorted2Other2Expected);
    }

    private PipelineTestCase<String> sorted2OtherSorted2() {
        final Comparator<Object> first = (o1, o2) -> 0;
        final Comparator<Object> second = (o1, o2) -> 1;

        final Pipeline<String> sorted2OtherSorted2 = createPipeline(
            operationFactory.createSorted(first), operationFactory.createSorted(second),
            operationFactory.createLimit(1),
            operationFactory.createSorted(second), operationFactory.createSorted(first)
        );


        final Pipeline<String> sorted2OtherSorted2Expected = createPipeline(
            operationFactory.createSorted(first.thenComparing(second)),
            operationFactory.createLimit(1),
            operationFactory.createSorted(second.thenComparing(first))
        );

        return new PipelineTestCase<>("Sorted 2, Other, Sorted 2", sorted2OtherSorted2, sorted2OtherSorted2Expected);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void assertArguments(Object[] expected, Object[] actual) {
        if (expected[0] instanceof Comparator && actual[0] instanceof Comparator) {
            final Comparator expectedPredicate = (Comparator) expected[0];
            final Comparator actualPredicate = (Comparator) actual[0];

            assertEquals(expectedPredicate.compare(null, null), actualPredicate.compare(null, null));
        } else {
            super.assertArguments(expected, actual);
        }
    }
}
