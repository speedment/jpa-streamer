package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

abstract class InternalOptimizerTest<ENTITY> {

    protected final PipelineFactory pipelineFactory = RootFactory.getOrThrow(PipelineFactory.class, ServiceLoader::load);
    protected final IntermediateOperationFactory iof = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
    protected final MoveAnonymousLambdaOperations optimizer = new MoveAnonymousLambdaOperations();

    abstract Class<ENTITY> getEntityClass();

    @TestFactory
    Stream<DynamicTest> modify() {
        return pipelines().map(testCase -> dynamicTest(testCase.getName(), () -> {
            this.optimizer.optimize(testCase.getPipeline());

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
        final Pipeline<ENTITY> unoptimized = testCase.getPipeline();
        final Pipeline<ENTITY> optimized = testCase.getExpectedPipeline();

        final List<IntermediateOperation<?, ?>> unoptimizedIntermediateOperations = unoptimized.intermediateOperations();
        final List<IntermediateOperation<?, ?>> optimizedIntermediateOperations = optimized.intermediateOperations();

        assertEquals(optimizedIntermediateOperations.size(), unoptimizedIntermediateOperations.size());

        for (int i = 0; i < unoptimizedIntermediateOperations.size(); i++) {
            final IntermediateOperation<?, ?> unoptimizedOperation = unoptimizedIntermediateOperations.get(i);
            final IntermediateOperation<?, ?> optimizedOperation = optimizedIntermediateOperations.get(i);

            assertEquals(optimizedOperation.type(), unoptimizedOperation.type());
            assertArguments(optimizedOperation.arguments(), unoptimizedOperation.arguments());
        }
    }

    protected void assertArguments(final Object[] expected, final Object[] actual) {
        if (expected != actual) {
            assertNotNull(expected);
            assertNotNull(actual);
            assertEquals(expected.length, actual.length);

            for(int i = 0; i < expected.length; ++i) {
                Object expectedElement = expected[i];
                Object actualElement = actual[i];
                if (expectedElement != actualElement) {
                    final Field[] actualDeclaredFields = actualElement.getClass().getDeclaredFields();
                    final Field[] expectedDeclaredFields = expectedElement.getClass().getDeclaredFields();
                    for (int j = 0; j < expectedDeclaredFields.length; ++j) {
                        assertEquals(actualDeclaredFields[i], expectedDeclaredFields[i]);
                    }
                }
            }
        }
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
