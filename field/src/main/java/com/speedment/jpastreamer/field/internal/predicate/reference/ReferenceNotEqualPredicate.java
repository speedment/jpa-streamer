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
package com.speedment.jpastreamer.field.internal.predicate.reference;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import java.util.Objects;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceNotEqualPredicate<ENTITY, V extends Comparable<? super V>>
extends AbstractFieldPredicate<ENTITY,
        HasReferenceValue<ENTITY, V>>
implements HasArg0<V> {

    private final V value;

    public ReferenceNotEqualPredicate(HasReferenceValue<ENTITY, V> field, V value) {
        super(PredicateType.NOT_EQUAL, field, entity -> {
            final V v = field.get(entity);
            return v != null && !Objects.equals(v, value);
        });
        this.value = value;
    }

    @Override
    public V get0() {
        return value;
    }

    @Override
    public ReferenceEqualPredicate<ENTITY, V> negate() {
        return new ReferenceEqualPredicate<>(getField(), value);
    }
}
