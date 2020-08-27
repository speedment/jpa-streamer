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
package com.speedment.jpastreamer.field.internal.predicate.bytes;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasByteValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of bytes.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ByteInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasByteValue<ENTITY>>
implements HasArg0<Set<Byte>> {
    
    private final Set<Byte> set;
    
    public ByteInPredicate(HasByteValue<ENTITY> field, Set<Byte> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsByte(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Byte> get0() {
        return set;
    }
    
    @Override
    public ByteNotInPredicate<ENTITY> negate() {
        return new ByteNotInPredicate<>(getField(), set);
    }
}