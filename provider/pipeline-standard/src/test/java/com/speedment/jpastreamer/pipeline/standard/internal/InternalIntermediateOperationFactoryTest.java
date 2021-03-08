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
package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.standard.internal.intermediate.InternalIntermediateOperationFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InternalIntermediateOperationFactoryTest {

    private final IntermediateOperationFactory factory = new InternalIntermediateOperationFactory();

    @Test
    void createFilter() {
        final Predicate<String> predicate = String::isEmpty;

        final IntermediateOperation<Stream<String>, Stream<String>> filter = factory.createFilter(predicate);

        assertEquals(IntermediateOperationType.FILTER, filter.type());
        assertEquals(Stream.class, filter.streamType());

        assertEquals(Stream.class, filter.returnType());
        assertEquals(1, filter.arguments().length);
        assertEquals(predicate, filter.arguments()[0]);

        final List<String> expected = Stream.of("A", "")
                .filter(predicate)
                .collect(Collectors.toList());

        final List<String> actual = filter
                .function()
                .apply(Stream.of("A", ""))
                .collect(Collectors.toList());
        assertEquals(expected, actual);

        assertDoesNotThrow(filter::function);
        //assertThrows(ClassCastException.class, filter::predicate);
    }
}
