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


import com.speedment.runtime.compute.ToStringNullable;
import com.speedment.jpastreamer.field.internal.StringFieldImpl;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;
import com.speedment.jpastreamer.field.trait.HasStringOperators;

import javax.persistence.AttributeConverter;

/**
 * A field that represents a string column.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see  ComparableField
 * @see  HasStringOperators
 */
public interface StringField<ENTITY, D> extends
    ComparableField<ENTITY, D, String>,
    HasStringOperators<ENTITY>,
    ToStringNullable<ENTITY> {

    /**
     * Creates a new {@link StringField} using the default implementation. 
     * 
     * @param <ENTITY>    the entity type
     * @param <D>         the database type
     * @param table       the table that the field belongs to
     * @param columnName the name of the database column the field represents
     * @param getter      method reference to the getter in the entity
     * @param attributeConverterClass  the attribute converter class
     * @param unique      represented column only contains unique values
     *
     * @return            the created field
     */
    static <ENTITY, D> StringField<ENTITY, D> create(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, String> getter,
            Class<? extends AttributeConverter<String, ? super D>> attributeConverterClass,
            boolean unique) {
        
        return new StringFieldImpl<>(
                table, columnName, getter, attributeConverterClass, unique
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