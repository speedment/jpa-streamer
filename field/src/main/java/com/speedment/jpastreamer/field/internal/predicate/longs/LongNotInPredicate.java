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

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is not included in a set of longs.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class LongNotInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasLongValue<ENTITY>>
implements HasArg0<Set<Long>> {
    
    private final Set<Long> set;
    
    public LongNotInPredicate(HasLongValue<ENTITY> field, Set<Long> set) {
        super(PredicateType.NOT_IN, field, entity -> !set.contains(field.getAsLong(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Long> get0() {
        return set;
    }
    
    @Override
    public LongInPredicate<ENTITY> negate() {
        return new LongInPredicate<>(getField(), set);
    }
}