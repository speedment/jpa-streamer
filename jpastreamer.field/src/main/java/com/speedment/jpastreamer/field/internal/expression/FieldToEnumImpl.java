/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
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

import com.speedment.jpastreamer.field.ReferenceField;
import com.speedment.jpastreamer.field.expression.FieldToEnum;
import com.speedment.runtime.compute.ToEnum;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link FieldToEnum}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToEnumImpl<ENTITY, V, E extends Enum<E>>
extends AbstractFieldMapper<ENTITY, V, E, ToEnum<ENTITY, E>, Function<V, E>>
implements FieldToEnum<ENTITY, V, E> {

    private final Class<E> enumClass;

    public FieldToEnumImpl(ReferenceField<ENTITY, V> field,
                           Function<V, E> mapper,
                           Class<E> enumClass) {
        super(field, mapper);
        this.enumClass = requireNonNull(enumClass);
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public E apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}
