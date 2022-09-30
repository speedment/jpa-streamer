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
package com.speedment.jpastreamer.pipeline.standard.internal.pipeline;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalIntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.terminal.InternalTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardPipelineTest {

    final IntermediateOperationFactory iof = new InternalIntermediateOperationFactory();
    final TerminalOperationFactory tof = new InternalTerminalOperationFactory();

    @Test
    void testToString() {
        final Pipeline<String> pipeline = new StandardPipeline<>(String.class);

        pipeline.intermediateOperations().add(iof.createFilter(new StringLengthGreaterThanThree()));
        pipeline.intermediateOperations().add(iof.acquireDistinct());
        pipeline.intermediateOperations().add(iof.createSkip(1));
        pipeline.intermediateOperations().add(iof.createLimit(10));
        pipeline.terminatingOperation(tof.createCollect(Collectors.joining()));

        final String toString = pipeline.toString();
        final String s = toString.toLowerCase();

        assertTrue(s.contains("filter("));
        assertTrue(s.contains("distinct()"));
        assertTrue(s.contains("skip(1)"));
        assertTrue(s.contains("limit(10)"));
        assertTrue(s.contains("collect("));
    }

    private static final class StringLengthGreaterThanThree implements Predicate<String> {
        @Override
        public boolean test(String s) {
            return s.length() > 3;
        }
    }

}
