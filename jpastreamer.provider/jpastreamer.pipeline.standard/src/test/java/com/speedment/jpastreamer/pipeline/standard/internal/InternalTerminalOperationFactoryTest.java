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
package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.standard.internal.InternalTerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InternalTerminalOperationFactoryTest {

    private final TerminalOperationFactory factory = new InternalTerminalOperationFactory();

    @Test
    void createForEach() {
        final List<String> list = new ArrayList<>();
        final Consumer<String> consumer = list::add;

        final TerminalOperation<Stream<String>, Void> forEach = factory.createForEach(consumer);

        assertEquals(TerminalOperationType.FOR_EACH, forEach.type());
        assertEquals(Stream.class, forEach.streamType());

        assertEquals(void.class, forEach.returnType());
        assertEquals(1, forEach.arguments().length);
        assertEquals(consumer, forEach.arguments()[0]);

        forEach.consumer().accept(Stream.of("A", "B"));
        assertEquals(Arrays.asList("A", "B"), list);

        assertDoesNotThrow(forEach::consumer);
        assertThrows(ClassCastException.class, forEach::function);
        assertThrows(ClassCastException.class, forEach::predicate);
        assertThrows(ClassCastException.class, forEach::toDoubleFunction);
        assertThrows(ClassCastException.class, forEach::toIntFunction);
        assertThrows(ClassCastException.class, forEach::toDoubleFunction);


    }

}
