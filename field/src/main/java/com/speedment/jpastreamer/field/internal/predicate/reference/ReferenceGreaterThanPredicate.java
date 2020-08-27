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
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import static com.speedment.jpastreamer.field.predicate.PredicateType.GREATER_THAN;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceGreaterThanPredicate<ENTITY, V extends Comparable<? super V>>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, V>>
implements HasArg0<V> {
    
    private final V value;

    public ReferenceGreaterThanPredicate(HasReferenceValue<ENTITY, V> field, V value) {
        super(GREATER_THAN, field, entity -> {
            final V fieldValue = field.get(entity);
            if (fieldValue == null || value == null) {
                return false;
            } else return fieldValue.compareTo(value) > 0;
        });
        
        this.value = value;
    }

    @Override
    public V get0() {
        return value;
    }

    @Override
    public ReferenceLessOrEqualPredicate<ENTITY, V> negate() {
        return new ReferenceLessOrEqualPredicate<>(getField(), value);
    }
}