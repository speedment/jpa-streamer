/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.jpastreamer.field;

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.jpastreamer.field.internal.EnumFieldImpl;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.trait.HasStringOperators;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

/**
 * A field representing an {@code Enum} value in the entity.
 *
 * @author Emil Forslund
 * @since  3.0.10
 *
 * @see  ComparableField
 */
public interface EnumField<ENTITY, D, E extends Enum<E>>
extends ComparableField<ENTITY, D, E>,
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

    /**
     * A method that takes a value of type {@code D} and converts it into an enum for
     * this field.
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the db-type-to-enum mapper
     */
    Function<D, E> dbTypeToEnum();

    /**
     * A method that takes an enum and converts it into the corresponding database type {@code D}
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the enum-to-db-type mapper
     */
    Function<E, D> enumToDbType();

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
     * @param <D>           the database type
     * @param <E>           the java enum type
     * @param table         the table that this field belongs to
     * @param columnName the name of the database column the field represents
     * @param getter        method reference to the getter in the entity
     * @param attributeConverterClass    the attribute converter class
     * @param enumToDbType  method to convert enum to the database type
     * @param dbTypeToEnum  method to convert the database type to enum
     * @param enumClass     the enum class
     *
     * @return            the created field
     */
    static <ENTITY, D, E extends Enum<E>> EnumField<ENTITY, D, E> create(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, E> getter,
            Class<? extends AttributeConverter<E, ? super D>> attributeConverterClass,
            Function<E, D> enumToDbType,
            Function<D, E> dbTypeToEnum,
            Class<E> enumClass) {

        return new EnumFieldImpl<>(
                table, columnName, getter, attributeConverterClass,
                enumToDbType, dbTypeToEnum, enumClass
        );
    }
}