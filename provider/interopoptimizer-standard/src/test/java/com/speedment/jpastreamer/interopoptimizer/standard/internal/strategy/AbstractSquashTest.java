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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.abstracts.AbstractSquash;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

abstract class AbstractSquashTest<ENTITY, T extends AbstractSquash<?>> {

    protected final PipelineFactory pipelineFactory = RootFactory.getOrThrow(PipelineFactory.class, ServiceLoader::load);
    protected final IntermediateOperationFactory operationFactory = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);

    abstract T getSquashInstance();

    abstract Class<ENTITY> getEntityClass();

    @TestFactory
    Stream<DynamicTest> optimize() {
        return pipelines().map(testCase -> dynamicTest(testCase.getName(), () -> {
            getSquashInstance().optimize(testCase.getPipeline());

            assertTestCase(testCase);
        }));
    }

    protected abstract Stream<PipelineTestCase<ENTITY>> pipelines();

    protected Pipeline<ENTITY> createPipeline(final IntermediateOperation<?, ?>... operations) {
        final Pipeline<ENTITY> pipeline = pipelineFactory.createPipeline(getEntityClass());

        for (IntermediateOperation<?, ?> operation : operations) {
            pipeline.intermediateOperations().add(operation);
        }

        return pipeline;
    }

    private void assertTestCase(final PipelineTestCase<ENTITY> testCase) {
        final Pipeline<ENTITY> squashed = testCase.getPipeline();
        final Pipeline<ENTITY> expected = testCase.getExpectedPipeline();

        final List<IntermediateOperation<?, ?>> squashedOperations = squashed.intermediateOperations();
        final List<IntermediateOperation<?, ?>> expectedOperations = expected.intermediateOperations();

        assertEquals(expectedOperations.size(), squashedOperations.size());

        for (int i = 0; i < squashedOperations.size(); i++) {
            final IntermediateOperation<?, ?> squashedOperation = squashedOperations.get(i);
            final IntermediateOperation<?, ?> expectedOperation = expectedOperations.get(i);

            assertEquals(expectedOperation.type(), squashedOperation.type());
            assertArguments(expectedOperation.arguments(), squashedOperation.arguments());
        }
    }

    protected void assertArguments(final Object[] expected, final Object[] actual) {
        assertArrayEquals(expected, actual);
    }

    protected static final class PipelineTestCase<ENTITY> {

        private final String name;
        private final Pipeline<ENTITY> pipeline;
        private final Pipeline<ENTITY> expectedPipeline;

        protected PipelineTestCase(
            final String name,
            final Pipeline<ENTITY> pipeline,
            final Pipeline<ENTITY> expectedPipeline
        ) {
            this.name = name;
            this.pipeline = pipeline;
            this.expectedPipeline = expectedPipeline;
        }

        public String getName() {
            return name;
        }

        public Pipeline<ENTITY> getPipeline() {
            return pipeline;
        }

        public Pipeline<ENTITY> getExpectedPipeline() {
            return expectedPipeline;
        }
    }
}
