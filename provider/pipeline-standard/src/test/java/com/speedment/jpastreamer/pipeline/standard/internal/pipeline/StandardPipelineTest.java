package com.speedment.jpastreamer.pipeline.standard.internal.pipeline;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.terminal.InternalTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StandardPipelineTest {

    final IntermediateOperationFactory iof = new InternalIntermediateOperationFactory();
    final TerminalOperationFactory tof = new InternalTerminalOperationFactory();

    @Test
    void testToString() {
        final Pipeline<String> pipeline = new StandardPipeline<>(String.class);

        pipeline.intermediateOperations().add(iof.createFilter(new StringLengthGreaterThanThree()));
        pipeline.intermediateOperations().add(iof.createDistinct());
        pipeline.intermediateOperations().add(iof.createSkip(1));
        pipeline.intermediateOperations().add(iof.createLimit(10));
        pipeline.terminatingOperation(tof.createCollect(Collectors.joining()));

        System.out.println(pipeline);

    }

    private static final class StringLengthGreaterThanThree implements Predicate<String> {
        @Override
        public boolean test(String s) {
            return s.length() > 3;
        }
    }


}