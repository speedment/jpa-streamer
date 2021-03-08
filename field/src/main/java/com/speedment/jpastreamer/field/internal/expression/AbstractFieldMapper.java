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
package com.speedment.jpastreamer.field.internal.expression;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldMapper;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation of {@link FieldMapper}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
abstract class AbstractFieldMapper<ENTITY, V, T, NON_NULLABLE extends Expression<ENTITY>, MAPPER>
implements FieldMapper<ENTITY, V, T, NON_NULLABLE, MAPPER> {

    final ReferenceField<ENTITY, V> field;
    final MAPPER mapper;

    AbstractFieldMapper(ReferenceField<ENTITY, V> field, MAPPER mapper) {
        this.field  = requireNonNull(field);
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public ReferenceField<ENTITY, V> getField() {
        return field;
    }

    @Override
    public MAPPER getMapper() {
        return mapper;
    }

    @Override
    public FieldIsNullPredicate<ENTITY, T> isNull() {
        return new MapperIsNull<>(this, field);
    }

    @Override
    public FieldIsNotNullPredicate<ENTITY, T> isNotNull() {
        return new MapperIsNotNull<>(this, field);
    }

    private static final class MapperIsNull<ENTITY, V, T>
    implements FieldIsNullPredicate<ENTITY, T> {

        private final ToNullable<ENTITY, T, ?> expression;
        private final ReferenceField<ENTITY, V> field;

        MapperIsNull(ToNullable<ENTITY, T, ?> expression,
                     ReferenceField<ENTITY, V> field) {
            this.expression = requireNonNull(expression);
            this.field      = requireNonNull(field);
        }

        @Override
        public boolean test(ENTITY value) {
            return field.get(value) == null;
        }

        @Override
        public FieldIsNotNullPredicate<ENTITY, T> negate() {
            return new MapperIsNotNull<>(expression, field);
        }

        @Override
        public ToNullable<ENTITY, T, ?> expression() {
            return expression;
        }

        @Override
        public Field<ENTITY> getField() {
            return field;
        }
    }

    private static final class MapperIsNotNull<ENTITY, V, T>
        implements FieldIsNotNullPredicate<ENTITY, T> {

        private final ToNullable<ENTITY, T, ?> expression;
        private final ReferenceField<ENTITY, V> field;

        MapperIsNotNull(ToNullable<ENTITY, T, ?> expression,
                        ReferenceField<ENTITY, V> field) {
            this.expression = requireNonNull(expression);
            this.field      = requireNonNull(field);
        }

        @Override
        public boolean test(ENTITY value) {
            return field.get(value) != null;
        }

        @Override
        public FieldIsNullPredicate<ENTITY, T> negate() {
            return new MapperIsNull<>(expression, field);
        }

        @Override
        public ToNullable<ENTITY, T, ?> expression() {
            return expression;
        }

        @Override
        public Field<ENTITY> getField() {
            return field;
        }
    }
}
