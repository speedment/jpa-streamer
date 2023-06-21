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
package com.speedment.jpastreamer.field.predicate;



/**
 * The predicate types that exists in Speedment. A predicate type must always
 * have a negated {@code PredicateType} that is the exact negation of itself.
 *
 * @author  Per Minborg
 * @since   2.1.0
 */

public enum PredicateType {

    // Constants
    ALWAYS_TRUE,
    ALWAYS_FALSE,
    
    // Reference
    IS_NULL,
    IS_NOT_NULL,
    
    // Comparable
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_OR_EQUAL,
    LESS_THAN,
    LESS_OR_EQUAL,
    BETWEEN,
    NOT_BETWEEN,
    IN,
    NOT_IN,
    
    // String
    EQUAL_IGNORE_CASE,
    NOT_EQUAL_IGNORE_CASE,
    STARTS_WITH,
    NOT_STARTS_WITH,
    STARTS_WITH_IGNORE_CASE,
    NOT_STARTS_WITH_IGNORE_CASE,
    ENDS_WITH,
    NOT_ENDS_WITH,
    ENDS_WITH_IGNORE_CASE,
    NOT_ENDS_WITH_IGNORE_CASE,
    CONTAINS,
    NOT_CONTAINS,
    CONTAINS_IGNORE_CASE,
    NOT_CONTAINS_IGNORE_CASE,
    IS_EMPTY,
    IS_NOT_EMPTY;

    private PredicateType negatedType;

    public PredicateType negate() {
        return negatedType;
    }

    public PredicateType effectiveType(boolean negate) {
        return negate ? negatedType : this;
    }
    
    static {
        associateNegations(ALWAYS_TRUE, ALWAYS_FALSE);
        associateNegations(IS_NULL, IS_NOT_NULL);
        associateNegations(EQUAL, NOT_EQUAL);
        associateNegations(GREATER_THAN, LESS_OR_EQUAL);
        associateNegations(GREATER_OR_EQUAL, LESS_THAN);
        associateNegations(BETWEEN, NOT_BETWEEN);
        associateNegations(IN, NOT_IN);
        associateNegations(EQUAL_IGNORE_CASE, NOT_EQUAL_IGNORE_CASE);
        associateNegations(STARTS_WITH, NOT_STARTS_WITH);
        associateNegations(STARTS_WITH_IGNORE_CASE, NOT_STARTS_WITH_IGNORE_CASE);
        associateNegations(ENDS_WITH, NOT_ENDS_WITH);
        associateNegations(ENDS_WITH_IGNORE_CASE, NOT_ENDS_WITH_IGNORE_CASE);
        associateNegations(CONTAINS, NOT_CONTAINS);
        associateNegations(CONTAINS_IGNORE_CASE, NOT_CONTAINS_IGNORE_CASE);
        associateNegations(IS_EMPTY, IS_NOT_EMPTY);
    }

    private static void associateNegations(PredicateType a, PredicateType b) {
        a.negatedType = b;
        b.negatedType = a;
    }
}
