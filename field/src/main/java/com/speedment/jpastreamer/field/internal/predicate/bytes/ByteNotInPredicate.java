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
package com.speedment.jpastreamer.field.internal.predicate.bytes;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasByteValue;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that evaluates if a value is not included in a set of bytes.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class ByteNotInPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasByteValue<ENTITY>>
implements HasArg0<Set<Byte>> {
    
    private final Set<Byte> set;
    
    public ByteNotInPredicate(HasByteValue<ENTITY> field, Set<Byte> set) {
        super(PredicateType.NOT_IN, field, entity -> !set.contains(field.getAsByte(entity)));
        this.set = requireNonNull(set);
    }
    
    @Override
    public Set<Byte> get0() {
        return set;
    }
    
    @Override
    public ByteInPredicate<ENTITY> negate() {
        return new ByteInPredicate<>(getField(), set);
    }
}