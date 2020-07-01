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

package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.stream.IntStream;
import java.util.stream.Stream;

final class SquashSkipTest extends AbstractSquashTest<String, SquashSkip> {

    @Override
    SquashSkip getSquashInstance() {
        return new SquashSkip(operationFactory);
    }

    @Override
    Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    protected Stream<PipelineTestCase<String>> pipelines() {
        return Stream.of(
            noSkip(),
            skipN(1), skipN(2), skipN(3), skipN(10), skipN(100),
            skipOther(),
            skip2Other2(),
            skip2OtherSkip2()
        );
    }

    private PipelineTestCase<String> noSkip() {
        final Pipeline<String> noSkip = createPipeline(
            operationFactory.createLimit(1)
        );

        final Pipeline<String> noSkipExpected = createPipeline(
            operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("No Skip", noSkip, noSkipExpected);
    }

    private PipelineTestCase<String> skipN(int n) {
        IntermediateOperation<?, ?>[] operations = IntStream.range(0, n).mapToObj(i -> operationFactory.createSkip(1)).toArray(IntermediateOperation[]::new);

        final Pipeline<String> skip = createPipeline(operations);

        final Pipeline<String> skipExpected = createPipeline(operationFactory.createSkip(n));

        return new PipelineTestCase<>("Skip " + n, skip, skipExpected);
    }

    private PipelineTestCase<String> skipOther() {
        final Pipeline<String> skipOther = createPipeline(
            operationFactory.createSkip(1),
            operationFactory.createLimit(1)
        );

        final Pipeline<String> skipOtherExpected = createPipeline(
            operationFactory.createSkip(1),
            operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("Skip, Other", skipOther, skipOtherExpected);
    }

    private PipelineTestCase<String> skip2Other2() {
        final Pipeline<String> skip2Other2 = createPipeline(
            operationFactory.createSkip(1), operationFactory.createSkip(1),
            operationFactory.createLimit(1), operationFactory.createLimit(1)
        );

        final Pipeline<String> skip2Other2Expected = createPipeline(
            operationFactory.createSkip(2),
            operationFactory.createLimit(1), operationFactory.createLimit(1)
        );

        return new PipelineTestCase<>("Skip 2, Other 2", skip2Other2, skip2Other2Expected);
    }

    private PipelineTestCase<String> skip2OtherSkip2() {
        final Pipeline<String> skip2OtherSkip2 = createPipeline(
            operationFactory.createSkip(1), operationFactory.createSkip(1),
            operationFactory.createLimit(1),
            operationFactory.createSkip(1), operationFactory.createSkip(1)
        );

        final Pipeline<String> skip2OtherSkip2Expected = createPipeline(
            operationFactory.createSkip(2),
            operationFactory.createLimit(1),
            operationFactory.createSkip(2)
        );

        return new PipelineTestCase<>("Skip 2, Other, Skip 2", skip2OtherSkip2, skip2OtherSkip2Expected);
    }
}
