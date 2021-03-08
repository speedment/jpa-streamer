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

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.jpastreamer.field.expression.FieldToBoolean;
import com.speedment.runtime.compute.ToBoolean;
import com.speedment.jpastreamer.field.ReferenceField;

/**
 * Default implementation of {@link FieldToBoolean}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToBooleanImpl<ENTITY, V>
    extends AbstractFieldMapper<ENTITY, V, Boolean, ToBoolean<ENTITY>, ToBooleanFunction<V>>
    implements FieldToBoolean<ENTITY, V> {

    public FieldToBooleanImpl(
        final ReferenceField<ENTITY, V> field,
        final ToBooleanFunction<V> mapper
    ) {
        super(field, mapper);
    }

    @Override
    public Boolean apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.applyAsBoolean(value);
    }

    @Override
    public boolean applyAsBoolean(ENTITY object) {
        return mapper.applyAsBoolean(field.get(object));
    }
}
