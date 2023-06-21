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
package com.speedment.jpastreamer.criteria.standard.support;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;

public class StringNotEqualPredicate implements FieldPredicate<String> {

    private final String value;

    public StringNotEqualPredicate(String value) {
        this.value = value;
    }

    @Override
    public FieldPredicate<String> negate() {
        return new StringEqualPredicate(value);
    }

    @Override
    public Field<String> getField() {
        return null;
    }

    @Override
    public PredicateType getPredicateType() {
        return PredicateType.NOT_EQUAL;
    }

    @Override
    public boolean applyAsBoolean(String o) {
        return !value.equals(o);
    }
}
