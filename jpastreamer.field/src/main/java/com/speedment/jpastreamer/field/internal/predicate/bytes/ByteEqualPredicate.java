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
package com.speedment.jpastreamer.field.internal.predicate.bytes;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasByteValue;

/**
 * A predicate that evaluates if a value is {@code ==} a specified {@code byte}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ByteEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasByteValue<ENTITY>>
implements HasArg0<Byte> {
    
    private final byte value;

    public ByteEqualPredicate(HasByteValue<ENTITY> field, byte value) {
        super(PredicateType.EQUAL, field, entity -> field.getAsByte(entity) == value);
        this.value = value;
    }
    
    @Override
    public Byte get0() {
        return value;
    }
    
    @Override
    public ByteNotEqualPredicate<ENTITY> negate() {
        return new ByteNotEqualPredicate<>(getField(), value);
    }
}
