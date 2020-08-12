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
package com.speedment.jpastreamer.field.internal.predicate.chars;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasCharValue;
import com.speedment.jpastreamer.field.predicate.PredicateType;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is included in a set of chars.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class CharInPredicate<ENTITY, D>
extends AbstractFieldPredicate<ENTITY, HasCharValue<ENTITY>>
implements HasArg0<Set<Character>> {
    
    private final Set<Character> set;
    
    public CharInPredicate(HasCharValue<ENTITY> field, Set<Character> set) {
        super(PredicateType.IN, field, entity -> set.contains(field.getAsChar(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Character> get0() {
        return set;
    }
    
    @Override
    public CharNotInPredicate<ENTITY, D> negate() {
        return new CharNotInPredicate<>(getField(), set);
    }
}