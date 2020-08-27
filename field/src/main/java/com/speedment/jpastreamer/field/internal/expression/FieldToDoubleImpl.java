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

import com.speedment.jpastreamer.field.expression.FieldToDouble;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.jpastreamer.field.ReferenceField;

import java.util.function.ToDoubleFunction;

/**
 * Default implementation of {@link FieldToDouble}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToDoubleImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Double, ToDouble<ENTITY>, ToDoubleFunction<V>>
implements FieldToDouble<ENTITY, V> {

    public FieldToDoubleImpl(ReferenceField<ENTITY, V> field,
                             ToDoubleFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Double apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsDouble(value);
    }

    @Override
    public double applyAsDouble(ENTITY object) {
        return mapper.applyAsDouble(field.get(object));
    }
}