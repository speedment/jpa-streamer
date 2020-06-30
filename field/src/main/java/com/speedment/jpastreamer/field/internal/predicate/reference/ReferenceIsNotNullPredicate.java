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
package com.speedment.jpastreamer.field.internal.predicate.reference;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceIsNotNullPredicate<ENTITY, V>
extends AbstractFieldPredicate<ENTITY,
        HasReferenceValue<ENTITY, V>> {

    public ReferenceIsNotNullPredicate(HasReferenceValue<ENTITY, V> field) {
        super(PredicateType.IS_NOT_NULL, field, entity -> entity != null && field.get(entity) != null);
    }

    @Override
    public ReferenceIsNullPredicate<ENTITY, V> negate() {
        return new ReferenceIsNullPredicate<>(getField());
    }
}