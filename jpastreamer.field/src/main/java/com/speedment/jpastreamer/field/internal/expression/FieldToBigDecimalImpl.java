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

import com.speedment.jpastreamer.field.expression.FieldToBigDecimal;
import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.jpastreamer.field.ReferenceField;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Default implementation of {@link FieldToBigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToBigDecimalImpl<ENTITY, V>
extends AbstractFieldMapper<ENTITY, V, BigDecimal, ToBigDecimal<ENTITY>, Function<V, BigDecimal>>
implements FieldToBigDecimal<ENTITY, V> {

    public FieldToBigDecimalImpl(ReferenceField<ENTITY, V> field,
                                 Function<V, BigDecimal> mapper) {
        super(field, mapper);
    }

    @Override
    public BigDecimal apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}
