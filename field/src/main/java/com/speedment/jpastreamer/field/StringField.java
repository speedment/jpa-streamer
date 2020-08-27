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
package com.speedment.jpastreamer.field;

import com.speedment.runtime.compute.ToStringNullable;
import com.speedment.jpastreamer.field.internal.StringFieldImpl;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;
import com.speedment.jpastreamer.field.trait.HasStringOperators;

/**
 * A field that represents a string column.
 * 
 * @param <ENTITY>  the entity type
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see  ComparableField
 * @see  HasStringOperators
 */
public interface StringField<ENTITY> extends
    ComparableField<ENTITY, String>,
    HasStringOperators<ENTITY>,
    ToStringNullable<ENTITY> {

    /**
     * Creates a new {@link StringField} using the default implementation. 
     * 
     * @param <ENTITY>    the entity type
     * @param table       the table that the field belongs to
     * @param columnName the name of the database column the field represents
     * @param getter      method reference to the getter in the entity
     * @param unique      represented column only contains unique values
     *
     * @return            the created field
     */
    static <ENTITY> StringField<ENTITY> create(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, String> getter,
            boolean unique) {
        
        return new StringFieldImpl<>(
                table, columnName, getter, unique
        );
    }

    @Override
    FieldIsNullPredicate<ENTITY, String> isNull();

    @Override
    default FieldIsNotNullPredicate<ENTITY, String> isNotNull() {
        return isNull().negate();
    }

    @Override
    default String apply(ENTITY object) {
        return get(object);
    }
}