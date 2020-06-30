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

/**
 * A predicate that evaluates if a value is {@code !=} a specified {@code byte}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ByteNotEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasByteValue<ENTITY>>
implements HasArg0<Byte> {
    
    private final byte value;
    
    public ByteNotEqualPredicate(HasByteValue<ENTITY> field, byte value) {
        super(PredicateType.NOT_EQUAL, field, entity -> field.getAsByte(entity) != value);
        this.value = value;
    }
    
    @Override
    public Byte get0() {
        return value;
    }
    
    @Override
    public ByteEqualPredicate<ENTITY> negate() {
        return new ByteEqualPredicate<>(getField(), value);
    }
}