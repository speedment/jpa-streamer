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