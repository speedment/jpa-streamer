/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.speedment.jpastreamer.preoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashLimitTest extends AbstractSquashTest<String, SquashLimit> {

    @Override
    SquashLimit getSquashInstance() {
        return new SquashLimit(operationFactory);
    }

    @Override
    Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    protected Stream<PipelineTestCase<String>> pipelines() {
        return Stream.of(
            noLimit(),
            limitN(1), limitN(2), limitN(3), limitN(10), limitN(100),
            limitOther(),
            limit2Other2(),
            limit2OtherLimit2()
        );
    }

    private PipelineTestCase<String> noLimit() {
        final Pipeline<String> noLimit = createPipeline(
            operationFactory.createSkip(1)
        );

        final Pipeline<String> noLimitExpected = createPipeline(
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("No Limit", noLimit, noLimitExpected);
    }

    private PipelineTestCase<String> limitN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.rangeClosed(1, n).mapToObj(operationFactory::createLimit).toArray(IntermediateOperation[]::new);

        final Pipeline<String> limit = createPipeline(operations);

        final Pipeline<String> limitExpected = createPipeline(operationFactory.createLimit(1));

        return new PipelineTestCase<>("Limit " + n, limit, limitExpected);
    }

    private PipelineTestCase<String> limitOther() {
        final Pipeline<String> limitOther = createPipeline(
            operationFactory.createLimit(1),
            operationFactory.createSkip(1)
        );

        final Pipeline<String> limitOtherExpected = createPipeline(
            operationFactory.createLimit(1),
            operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Limit, Other", limitOther, limitOtherExpected);
    }

    private PipelineTestCase<String> limit2Other2() {
        final Pipeline<String> limit2Other2 = createPipeline(
            operationFactory.createLimit(1), operationFactory.createLimit(2),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        final Pipeline<String> limit2Other2Expected = createPipeline(
            operationFactory.createLimit(1),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        return new PipelineTestCase<>("Limit 2, Other 2", limit2Other2, limit2Other2Expected);
    }

    private PipelineTestCase<String> limit2OtherLimit2() {
        final Pipeline<String> limit2OtherLimit2 = createPipeline(
            operationFactory.createLimit(1), operationFactory.createLimit(2),
            operationFactory.createSkip(1),
            operationFactory.createLimit(3), operationFactory.createLimit(4)
        );

        final Pipeline<String> limit2OtherLimit2Expected = createPipeline(
            operationFactory.createLimit(1),
            operationFactory.createSkip(1),
            operationFactory.createLimit(3)
        );

        return new PipelineTestCase<>("Limit 2, Other, Limit 2", limit2OtherLimit2, limit2OtherLimit2Expected);
    }
}
