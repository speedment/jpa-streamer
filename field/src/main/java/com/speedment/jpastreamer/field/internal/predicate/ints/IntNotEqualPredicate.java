/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.jpastreamer.field.internal.predicate.ints;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasIntValue;

/**
 * A predicate that evaluates if a value is {@code !=} a specified {@code int}.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class IntNotEqualPredicate<ENTITY, D>
extends AbstractFieldPredicate<ENTITY, HasIntValue<ENTITY, D>>
implements HasArg0<Integer> {
    
    private final int value;
    
    public IntNotEqualPredicate(HasIntValue<ENTITY, D> field, int value) {
        super(PredicateType.NOT_EQUAL, field, entity -> field.getAsInt(entity) != value);
        this.value = value;
    }
    
    @Override
    public Integer get0() {
        return value;
    }
    
    @Override
    public IntEqualPredicate<ENTITY, D> negate() {
        return new IntEqualPredicate<>(getField(), value);
    }
}