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
package com.speedment.jpastreamer.field;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.jpastreamer.field.internal.EnumFieldImpl;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;

import java.util.EnumSet;
import java.util.function.ToDoubleFunction;

/**
 * A field representing an {@code Enum} value in the entity.
 *
 * @author Emil Forslund
 * @since  3.0.10
 *
 * @see  ComparableField
 */
public interface EnumField<ENTITY, E extends Enum<E>>
extends ComparableField<ENTITY, E>,
        ToEnumNullable<ENTITY, E> {

    /**
     * Returns the enum class of this field.
     *
     * @return  the enum class
     * @since   3.0.13
     */
    Class<E> enumClass();

    /**
     * Returns the set of possible values for this enum. The order will be the
     * ordinal order of the enum.
     * <p>
     * This method creates a copy of the internal storage structure so changes
     * to the returned collection are allowed.
     *
     * @return  the constants
     */
    EnumSet<E> constants();

    @Override
    FieldIsNullPredicate<ENTITY, E> isNull();

    @Override
    default FieldIsNotNullPredicate<ENTITY, E> isNotNull() {
        return isNull().negate();
    }

    @Override
    default E apply(ENTITY entity) {
        return get(entity);
    }

    @Override
    default ToDoubleNullable<ENTITY> mapToDoubleIfPresent(ToDoubleFunction<E> mapper) {
        return ComparableField.super.mapToDoubleIfPresent(mapper);
    }

    /**
     * Create a new instance of this interface using the default implementation.
     *
     * @param <ENTITY>      the entity type
     * @param <E>           the java enum type
     * @param table         the table that this field belongs to
     * @param columnName the name of the database column the field represents
     * @param getter        method reference to the getter in the entity
     * @param enumClass     the enum class
     *
     * @return            the created field
     */
    static <ENTITY, E extends Enum<E>> EnumField<ENTITY, E> create(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, E> getter,
            Class<E> enumClass) {

        return new EnumFieldImpl<>(
                table, columnName, getter, enumClass
        );
    }
}
