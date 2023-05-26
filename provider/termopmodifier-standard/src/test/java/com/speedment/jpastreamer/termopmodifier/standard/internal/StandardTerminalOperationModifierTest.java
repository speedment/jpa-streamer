package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

abstract class StandardTerminalOperationModifierTest<ENTITY> {
    
    protected final PipelineFactory pipelineFactory = RootFactory.getOrThrow(PipelineFactory.class, ServiceLoader::load);
    protected final IntermediateOperationFactory iof = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
    protected final TerminalOperationFactory tof  = RootFactory.getOrThrow(TerminalOperationFactory.class, ServiceLoader::load);
    protected final TerminalOperationModifier modifier = new StandardTerminalOperatorModifier();

    abstract Class<ENTITY> getEntityClass();

    @TestFactory
    Stream<DynamicTest> modify() {
        return pipelines().map(testCase -> dynamicTest(testCase.getName(), () -> {
            this.modifier.modify(testCase.getPipeline());

            assertTestCase(testCase);
        }));
    }

    protected abstract Stream<PipelineTestCase<ENTITY>> pipelines();

    protected Pipeline<ENTITY> createPipeline(final TerminalOperation<?, ?> terminalOperation, final IntermediateOperation<?, ?>... operations) {
        final Pipeline<ENTITY> pipeline = pipelineFactory.createPipeline(getEntityClass());

        for (IntermediateOperation<?, ?> operation : operations) {
            pipeline.intermediateOperations().add(operation);
        }

        pipeline.terminatingOperation(terminalOperation);

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
