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
package com.speedment.jpastreamer.field.internal.predicate.string;

import com.speedment.jpastreamer.field.trait.HasReferenceValue;
import com.speedment.jpastreamer.field.predicate.PredicateType;

/**
 *
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class StringNotEndsWithPredicate<ENTITY>
extends AbstractStringPredicate<ENTITY> {

    public StringNotEndsWithPredicate(
            final HasReferenceValue<ENTITY, String> field,
            final String str) {

        super(PredicateType.NOT_ENDS_WITH, field, str, entity -> {
            final String fieldValue = field.get(entity);
            return fieldValue != null
                && !fieldValue.endsWith(str);
        });
    }

    @Override
    public StringEndsWithPredicate<ENTITY> negate() {
        return new StringEndsWithPredicate<>(getField(), get0());
    }
}