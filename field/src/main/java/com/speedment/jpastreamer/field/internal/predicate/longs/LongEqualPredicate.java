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
package com.speedment.jpastreamer.field.internal.predicate.longs;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasLongValue;

/**
 * A predicate that evaluates if a value is {@code ==} a specified {@code long}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class LongEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasLongValue<ENTITY>>
implements HasArg0<Long> {
    
    private final long value;
    
    public LongEqualPredicate(HasLongValue<ENTITY> field, long value) {
        super(PredicateType.EQUAL, field, entity -> field.getAsLong(entity) == value);
        this.value = value;
    }
    
    @Override
    public Long get0() {
        return value;
    }
    
    @Override
    public LongNotEqualPredicate<ENTITY> negate() {
        return new LongNotEqualPredicate<>(getField(), value);
    }
}
