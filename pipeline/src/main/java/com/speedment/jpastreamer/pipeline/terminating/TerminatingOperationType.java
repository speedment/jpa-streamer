package com.speedment.jpastreamer.pipeline.terminating;

public enum TerminatingOperationType {
    // BaseStream
    ITERATOR(OrderPreservation.REQUIRED),
    SPLITERATOR(OrderPreservation.REQUIRED),
    // Stream
    FOR_EACH(OrderPreservation.NOT_REQUIRED),
    FOR_EACH_ORDERED(OrderPreservation.REQUIRED),
    TO_ARRAY(OrderPreservation.REQUIRED),
    REDUCE(OrderPreservation.NOT_REQUIRED),
    COLLECT(OrderPreservation.NOT_REQUIRED),
    MIN(OrderPreservation.NOT_REQUIRED),
    MAX(OrderPreservation.NOT_REQUIRED),
    COUNT(OrderPreservation.NOT_REQUIRED), // TypePreservation.NOT_REQUIRED
    ANY_MATCH(OrderPreservation.NOT_REQUIRED),
    ALL_MATCH(OrderPreservation.NOT_REQUIRED),
    NONE_MATCH(OrderPreservation.NOT_REQUIRED),
    FIND_FIRST(OrderPreservation.REQUIRED),
    FIND_ANY(OrderPreservation.NOT_REQUIRED),
    // Primitive Stream Types
    SUM(OrderPreservation.NOT_REQUIRED),
    AVERAGE(OrderPreservation.NOT_REQUIRED),
    SUMMARY_STATISTICS(OrderPreservation.NOT_REQUIRED);

    private final OrderPreservation orderPreservation;

    TerminatingOperationType(OrderPreservation orderPreservation) {
        this.orderPreservation = orderPreservation;
    }

    public OrderPreservation orderPreservation() {
        return orderPreservation;
    }
}