package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InternalStandardIntermediateOperationFactoryTest {

    private final IntermediateOperationFactory factory = new InternalIntermediateOperationFactory();

    @Test
    void createFilter() {
        final Predicate<String> predicate = String::isEmpty;

        final IntermediateOperator<Stream<String>, Stream<String>> filter = factory.createFilter(predicate);

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