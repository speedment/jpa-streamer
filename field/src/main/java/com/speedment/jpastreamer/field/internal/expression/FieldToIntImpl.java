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

import com.speedment.runtime.compute.ToInt;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToInt;

import java.util.function.ToIntFunction;

/**
 * Default implementation of {@link FieldToInt}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToIntImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Integer, ToInt<ENTITY>, ToIntFunction<V>>
implements FieldToInt<ENTITY, V> {

    public FieldToIntImpl(ReferenceField<ENTITY, V> field,
                          ToIntFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Integer apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsInt(value);
    }

    @Override
    public int applyAsInt(ENTITY object) {
        return mapper.applyAsInt(field.get(object));
    }
}