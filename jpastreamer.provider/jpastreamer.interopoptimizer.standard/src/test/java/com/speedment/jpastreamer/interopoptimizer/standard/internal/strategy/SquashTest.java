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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.Squash;
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

abstract class SquashTest<ENTITY, T extends Squash> {

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
