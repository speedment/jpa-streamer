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
package com.speedment.jpastreamer.field.internal;

import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.internal.predicate.reference.ReferenceIsNullPredicate;
import com.speedment.jpastreamer.field.method.ReferenceGetter;

import javax.persistence.AttributeConverter;

import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <V>      the field value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class ReferenceFieldImpl<ENTITY, D, V> 
implements ReferenceField<ENTITY, D, V> {

    private final Class<ENTITY> table;
    private final String columnName;
    private final ReferenceGetter<ENTITY, V> getter;
    private final Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass;
    private final boolean unique;

    public ReferenceFieldImpl(
        final Class<ENTITY> table,
        final String columnName,
        final ReferenceGetter<ENTITY, V> getter,
        final Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass,
        final boolean unique
    ) {
        this.table = requireNonNull(table);
        this.columnName = requireNonNull(columnName);
        this.getter     = requireNonNull(getter);
        this.attributeConverterClass = attributeConverterClass;
        this.unique     = unique;
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Class<ENTITY> table() {
        return table;
    }

    @Override
    public ReferenceGetter<ENTITY, V> getter() {
        return getter;
    }

    @Override
    public Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass() {
        return attributeConverterClass;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }


    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public FieldPredicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public String columnName() {
        return columnName;
    }
}
