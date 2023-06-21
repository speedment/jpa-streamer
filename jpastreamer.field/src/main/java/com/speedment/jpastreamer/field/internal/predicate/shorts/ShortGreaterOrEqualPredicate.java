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
package com.speedment.jpastreamer.field.internal.predicate.shorts;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasShortValue;

/**
 * A predicate that evaluates if a value is {@code >=} a specified {@code
 * short}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ShortGreaterOrEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY>>
implements HasArg0<Short> {
    
    private final short value;
    
    public ShortGreaterOrEqualPredicate(HasShortValue<ENTITY> field, short value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsShort(entity) >= value);
        this.value = value;
    }
    
    @Override
    public Short get0() {
        return value;
    }
    
    @Override
    public ShortLessThanPredicate<ENTITY> negate() {
        return new ShortLessThanPredicate<>(getField(), value);
    }
}
