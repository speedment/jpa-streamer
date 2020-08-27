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

import com.speedment.common.function.ToCharFunction;
import com.speedment.runtime.compute.ToChar;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToChar;

/**
 * Default implementation of {@link FieldToChar}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToCharImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Character, ToChar<ENTITY>, ToCharFunction<V>>
implements FieldToChar<ENTITY, V> {

    public FieldToCharImpl(ReferenceField<ENTITY, V> field,
                           ToCharFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Character apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsChar(value);
    }

    @Override
    public char applyAsChar(ENTITY object) {
        return mapper.applyAsChar(field.get(object));
    }
}