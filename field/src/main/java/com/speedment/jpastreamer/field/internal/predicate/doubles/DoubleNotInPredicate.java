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
package com.speedment.jpastreamer.field.internal.predicate.doubles;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasDoubleValue;
import com.speedment.jpastreamer.field.predicate.PredicateType;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is not included in a set of doubles.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class DoubleNotInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasDoubleValue<ENTITY>>
implements HasArg0<Set<Double>> {
    
    private final Set<Double> set;
    
    public DoubleNotInPredicate(HasDoubleValue<ENTITY> field, Set<Double> set) {
        super(PredicateType.NOT_IN, field, entity -> !set.contains(field.getAsDouble(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Double> get0() {
        return set;
    }
    
    @Override
    public DoubleInPredicate<ENTITY> negate() {
        return new DoubleInPredicate<>(getField(), set);
    }
}
