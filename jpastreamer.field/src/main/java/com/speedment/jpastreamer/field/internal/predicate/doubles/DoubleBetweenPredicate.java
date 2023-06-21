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
package com.speedment.jpastreamer.field.internal.predicate.doubles;

import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasArg1;
import com.speedment.jpastreamer.field.trait.HasDoubleValue;
import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.predicate.trait.HasInclusion;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is between two doubles.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class DoubleBetweenPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasDoubleValue<ENTITY>>
implements HasInclusion,
        HasArg0<Double>,
        HasArg1<Double> {
    
    private final double start;
    private final double end;
    private final Inclusion inclusion;
    
    public DoubleBetweenPredicate(
            HasDoubleValue<ENTITY> field,
            double start,
            double end,
            Inclusion inclusion) {
        super(PredicateType.BETWEEN, field, entity -> {
            final double fieldValue = field.getAsDouble(entity);
            
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE :
                    return (start < fieldValue && end > fieldValue);
                
                case START_EXCLUSIVE_END_INCLUSIVE :
                    return (start < fieldValue && end >= fieldValue);
                
                case START_INCLUSIVE_END_EXCLUSIVE :
                    return (start <= fieldValue && end > fieldValue);
                
                case START_INCLUSIVE_END_INCLUSIVE :
                    return (start <= fieldValue && end >= fieldValue);
                
                default : throw new IllegalStateException("Inclusion unknown: " + inclusion);
            }
        });
        
        this.start     = start;
        this.end       = end;
        this.inclusion = requireNonNull(inclusion);
    }
    
    @Override
    public Double get0() {
        return start;
    }
    
    @Override
    public Double get1() {
        return end;
    }
    
    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }
    
    @Override
    public DoubleNotBetweenPredicate<ENTITY> negate() {
        return new DoubleNotBetweenPredicate<>(getField(), start, end, inclusion);
    }
}
