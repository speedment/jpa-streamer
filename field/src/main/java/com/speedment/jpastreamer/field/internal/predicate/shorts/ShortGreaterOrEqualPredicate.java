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
package com.speedment.jpastreamer.field.internal.predicate.shorts;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasShortValue;

/**
 * A predicate that evaluates if a value is {@code >=} a specified {@code
 * short}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ShortGreaterOrEqualPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY>>
implements HasArg0<Short> {
    
    private final short value;
    
    public ShortGreaterOrEqualPredicate(HasShortValue<ENTITY> field, short value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsShort(entity) >= value);
        this.value = value;
    }
    
    @Override
    public Short get0() {
        return value;
    }
    
    @Override
    public ShortLessThanPredicate<ENTITY> negate() {
        return new ShortLessThanPredicate<>(getField(), value);
    }
}