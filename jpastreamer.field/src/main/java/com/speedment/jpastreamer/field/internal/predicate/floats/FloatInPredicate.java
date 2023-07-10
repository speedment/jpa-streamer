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
package com.speedment.jpastreamer.field.internal.predicate.floats;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasFloatValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of floats.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class FloatInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasFloatValue<ENTITY>>
implements HasArg0<Set<Float>> {
    
    private final Set<Float> set;
    
    public FloatInPredicate(HasFloatValue<ENTITY> field, Set<Float> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsFloat(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Float> get0() {
        return set;
    }
    
    @Override
    public FloatNotInPredicate<ENTITY> negate() {
        return new FloatNotInPredicate<>(getField(), set);
    }
}
