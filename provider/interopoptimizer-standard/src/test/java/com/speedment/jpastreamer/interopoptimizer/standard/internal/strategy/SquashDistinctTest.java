/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashDistinctTest extends SquashTest<String, SquashDistinct> {

    @Override
    SquashDistinct getSquashInstance() {
        return new SquashDistinct(operationFactory);
    }

    @Override
    Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    protected Stream<PipelineTestCase<String>> pipelines() {
        return Stream.of(
            noDistinct(),
            distinctN(1), distinctN(2), distinctN(3), distinctN(10), distinctN(100),
            distinctOther(),
            distinct2Other2(),
            distinct2OtherDistinct2()
        );
    }

    private PipelineTestCase<String> noDistinct() {
        final Pipeline<String> noSkip = createPipeline(
            operationFactory.createLimit(1)
        );

        final Pipeline<String> noSkipExpected = createPipeline(
            operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("No Distinct", noSkip, noSkipExpected);
    }

    private PipelineTestCase<String> distinctN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.range(0, n).mapToObj(i -> operationFactory.acquireDistinct()).toArray(IntermediateOperation[]::new);

        final Pipeline<String> distinct = createPipeline(operations);

        final Pipeline<String> distinctExpected = createPipeline(operationFactory.acquireDistinct());

        return new PipelineTestCase<>("Distinct " + n, distinct, distinctExpected);
    }

    private PipelineTestCase<String> distinctOther() {
        final Pipeline<String> distinctOther = createPipeline(
            operationFactory.acquireDistinct(),
            operationFactory.createLimit(1)
        );

        final Pipeline<String> distinctOtherExpected = createPipeline(
            operationFactory.acquireDistinct(),
            operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("Distinct, Other", distinctOther, distinctOtherExpected);
    }

    private PipelineTestCase<String> distinct2Other2() {
        final Pipeline<String> distinct2Other2 = createPipeline(
            operationFactory.acquireDistinct(), operationFactory.acquireDistinct(),
            operationFactory.createLimit(1), operationFactory.createLimit(1)
        );

        final Pipeline<String> distinct2Other2Expected = createPipeline(
            operationFactory.acquireDistinct(),
            operationFactory.createLimit(1), operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("Distinct 2, Other 2", distinct2Other2, distinct2Other2Expected);
    }

    private PipelineTestCase<String> distinct2OtherDistinct2() {
        final Pipeline<String> distinct2OtherDistinct2 = createPipeline(
            operationFactory.acquireDistinct(), operationFactory.acquireDistinct(),
            operationFactory.createLimit(1),
            operationFactory.acquireDistinct(), operationFactory.acquireDistinct()
        );

        final Pipeline<String> distinct2OtherDistinct2Expected = createPipeline(
            operationFactory.acquireDistinct(),
            operationFactory.createLimit(1),
            operationFactory.acquireDistinct()
        );

        return new PipelineTestCase<>("Distinct 2, Other, Distinct 2", distinct2OtherDistinct2, distinct2OtherDistinct2Expected);
    }
}
