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

import com.speedment.common.function.ToByteFunction;
import com.speedment.jpastreamer.field.expression.FieldToByte;
import com.speedment.runtime.compute.ToByte;
import com.speedment.jpastreamer.field.ReferenceField;

/**
 * Default implementation of {@link FieldToByte}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToByteImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, Byte, ToByte<ENTITY>, ToByteFunction<V>>
implements FieldToByte<ENTITY, V> {

    public FieldToByteImpl(ReferenceField<ENTITY, V> field,
                           ToByteFunction<V> mapper) {
        super(field, mapper);
    }

    @Override
    public Byte apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsByte(value);
    }

    @Override
    public byte applyAsByte(ENTITY object) {
        return mapper.applyAsByte(field.get(object));
    }
}
