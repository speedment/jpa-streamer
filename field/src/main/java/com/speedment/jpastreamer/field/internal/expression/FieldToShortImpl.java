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
package com.speedment.jpastreamer.field.internal.expression;

import com.speedment.common.function.ToShortFunction;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToShort;
import com.speedment.runtime.compute.ToShort;

/**
 * Default implementation of {@link FieldToShort}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToShortImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Short, ToShort<ENTITY>, ToShortFunction<V>>
implements FieldToShort<ENTITY, V> {

    public FieldToShortImpl(ReferenceField<ENTITY, V> field,
                            ToShortFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Short apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsShort(value);
    }

    @Override
    public short applyAsShort(ENTITY object) {
        return mapper.applyAsShort(field.get(object));
    }
}