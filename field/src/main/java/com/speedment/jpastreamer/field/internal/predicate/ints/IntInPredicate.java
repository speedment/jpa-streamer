/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.field.internal.predicate.ints;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasIntValue;
import com.speedment.jpastreamer.field.predicate.PredicateType;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of ints.
 * 
 * @param <ENTITY> entity type
 * @author Emil Forslund
 *
 * @since  3.0.0
 */
public final class IntInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasIntValue<ENTITY>>
implements HasArg0<Set<Integer>> {
    
    private final Set<Integer> set;
    
    public IntInPredicate(HasIntValue<ENTITY> field, Set<Integer> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsInt(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Integer> get0() {
        return set;
    }
    
    @Override
    public IntNotInPredicate<ENTITY> negate() {
        return new IntNotInPredicate<>(getField(), set);
    }
}
