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
package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import javax.persistence.criteria.JoinType;

import static java.util.Objects.requireNonNull;

final class StandardJoinConfiguration<T> implements StreamConfiguration.JoinConfiguration<T> {

    private final Field<T> field;
    private final JoinType joinType;

    StandardJoinConfiguration(final Field<T> field, final JoinType joinType) {
        this.field = requireNonNull(field);
        this.joinType = requireNonNull(joinType);
    }

    @Override
    public Field<T> field() {
        return field;
    }

    @Override
    public JoinType joinType() {
        return joinType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandardJoinConfiguration<?> that = (StandardJoinConfiguration<?>) o;

        if (!field.equals(that.field)) return false;
        return joinType == that.joinType;
    }

    @Override
    public int hashCode() {
        int result = field.hashCode();
        result = 31 * result + joinType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return label(joinType) + " on " + field.columnName();
    }

    private static String label(final JoinType joinType) {
        switch (joinType) {
            case INNER: return "inner join";
            case LEFT: return "left outer join";
            case RIGHT: return "right outer join";
        }
        return joinType.name();
    }

}
