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

import com.speedment.jpastreamer.field.internal.ComparableFieldImpl;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.trait.HasComparableOperators;

/**
 * A field that represents an object value that implements {@code Comparable}.
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the field value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see    ReferenceField
 */
public interface ComparableField<ENTITY, V extends Comparable<? super V>>
extends ReferenceField<ENTITY, V>,
        HasComparableOperators<ENTITY, V> {
    /**
     * Creates a new {@link ComparableField} using the default implementation. 
     * 
     * @param <ENTITY>    the entity type
     * @param <V>         the field value type
     * @param table       the table the field belongs to
     * @param columnName the name of the database column the field represents
     * @param getter      method reference to the getter in the entity
     * @param unique      represented column only contains unique values
     *
     * @return            the created field
     */
    static <ENTITY, V extends Comparable<? super V>>
    ComparableField<ENTITY, V> create(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, V> getter,
            boolean unique) {
        
        return new ComparableFieldImpl<>(
                table, columnName, getter, unique
        );
    }

}