package com.speedment.jpastreamer.pipeline.terminal;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFunctionalType;

import static com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFunctionalType.*;
import static java.util.Objects.requireNonNull;

public enum TerminalOperationType {
    // BaseStream
    ITERATOR(APPLY, OrderPreservation.REQUIRED),
    SPLITERATOR(APPLY, OrderPreservation.REQUIRED),
    // Stream
    FOR_EACH(ACCEPT, OrderPreservation.NOT_REQUIRED),
    FOR_EACH_ORDERED(ACCEPT, OrderPreservation.REQUIRED),
    TO_ARRAY(APPLY, OrderPreservation.REQUIRED),
    REDUCE(APPLY, OrderPreservation.NOT_REQUIRED),
    COLLECT(APPLY, OrderPreservation.NOT_REQUIRED),
    MIN(APPLY, OrderPreservation.NOT_REQUIRED),
    MAX(APPLY, OrderPreservation.NOT_REQUIRED),
    COUNT(APPLY_AS_LONG, OrderPreservation.NOT_REQUIRED), // TypePreservation.NOT_REQUIRED
    ANY_MATCH(TEST, OrderPreservation.NOT_REQUIRED),
    ALL_MATCH(TEST, OrderPreservation.NOT_REQUIRED),
    NONE_MATCH(TEST, OrderPreservation.NOT_REQUIRED),
    FIND_FIRST(APPLY, OrderPreservation.REQUIRED),
    FIND_ANY(APPLY, OrderPreservation.NOT_REQUIRED),
    // Primitive Stream Types
    SUM_INT(APPLY_AS_INT, OrderPreservation.NOT_REQUIRED),
    SUM_LONG(APPLY_AS_LONG, OrderPreservation.NOT_REQUIRED),
    SUM_DOUBLE(APPLY_AS_DOUBLE, OrderPreservation.NOT_REQUIRED),
    AVERAGE(APPLY, OrderPreservation.NOT_REQUIRED),
    SUMMARY_STATISTICS(APPLY, OrderPreservation.NOT_REQUIRED);

    private final TerminalOperationFunctionalType functionalType;
    private final OrderPreservation orderPreservation;

    TerminalOperationType(final TerminalOperationFunctionalType functionalType, final OrderPreservation orderPreservation) {
        this.functionalType = requireNonNull(functionalType);
        this.orderPreservation = requireNonNull(orderPreservation);
    }

    public TerminalOperationFunctionalType functionalType() {
        return functionalType;
    }

    public OrderPreservation orderPreservation() {
        return orderPreservation;
    }
}