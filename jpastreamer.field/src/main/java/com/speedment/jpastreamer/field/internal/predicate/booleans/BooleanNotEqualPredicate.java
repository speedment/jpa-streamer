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
package com.speedment.jpastreamer.field.internal.predicate.booleans;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasBooleanValue;

/**
 * A predicate that evaluates if a value is {@code !=} a specified {@code
 * boolean}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class BooleanNotEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasBooleanValue<ENTITY>>
implements HasArg0<Boolean> {
    
    private final boolean value;
    
    public BooleanNotEqualPredicate(HasBooleanValue<ENTITY> field, boolean value) {
        super(PredicateType.NOT_EQUAL, field, entity -> field.getAsBoolean(entity) != value);
        this.value = value;
    }
    
    @Override
    public Boolean get0() {
        return value;
    }
    
    @Override
    public BooleanEqualPredicate<ENTITY> negate() {
        return new BooleanEqualPredicate<>(getField(), value);
    }
}
