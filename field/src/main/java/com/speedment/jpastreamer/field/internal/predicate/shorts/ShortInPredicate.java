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
package com.speedment.jpastreamer.field.internal.predicate.shorts;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasShortValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of shorts.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ShortInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY>>
implements HasArg0<Set<Short>> {
    
    private final Set<Short> set;
    
    public ShortInPredicate(HasShortValue<ENTITY> field, Set<Short> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsShort(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Short> get0() {
        return set;
    }
    
    @Override
    public ShortNotInPredicate<ENTITY> negate() {
        return new ShortNotInPredicate<>(getField(), set);
    }
}
