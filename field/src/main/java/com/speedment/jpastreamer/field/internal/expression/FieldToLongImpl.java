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

import com.speedment.jpastreamer.field.expression.FieldToLong;
import com.speedment.runtime.compute.ToLong;
import com.speedment.jpastreamer.field.ReferenceField;

import java.util.function.ToLongFunction;

/**
 * Default implementation of {@link FieldToLong}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToLongImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Long, ToLong<ENTITY>, ToLongFunction<V>>
implements FieldToLong<ENTITY, V> {

    public FieldToLongImpl(ReferenceField<ENTITY, V> field,
                           ToLongFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Long apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsLong(value);
    }

    @Override
    public long applyAsLong(ENTITY object) {
        return mapper.applyAsLong(field.get(object));
    }
}
