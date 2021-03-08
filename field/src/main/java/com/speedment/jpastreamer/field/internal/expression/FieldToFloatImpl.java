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

import com.speedment.common.function.ToFloatFunction;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToFloat;
import com.speedment.runtime.compute.ToFloat;

/**
 * Default implementation of {@link FieldToFloat}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToFloatImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Float, ToFloat<ENTITY>, ToFloatFunction<V>>
implements FieldToFloat<ENTITY, V> {

    public FieldToFloatImpl(ReferenceField<ENTITY, V> field,
                            ToFloatFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Float apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsFloat(value);
    }

    @Override
    public float applyAsFloat(ENTITY object) {
        return mapper.applyAsFloat(field.get(object));
    }
}
