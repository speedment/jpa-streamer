/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.pipeline.terminal;

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
    REDUCE(APPLY, OrderPreservation.NOT_REQUIRED_IF_PARALLEL),
    COLLECT(APPLY, OrderPreservation.NOT_REQUIRED_IF_PARALLEL),
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
