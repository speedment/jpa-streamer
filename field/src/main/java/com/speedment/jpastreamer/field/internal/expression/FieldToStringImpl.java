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

import com.speedment.runtime.compute.ToString;
import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToString;

import java.util.function.Function;

/**
 * Default implementation of {@link FieldToString}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToStringImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, String, ToString<ENTITY>, Function<V, String>>
implements FieldToString<ENTITY, V> {

    public FieldToStringImpl(ReferenceField<ENTITY, V> field,
                             Function<V, String> mapper) {
        super(field, mapper);
    }

    @Override
    public String apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}
