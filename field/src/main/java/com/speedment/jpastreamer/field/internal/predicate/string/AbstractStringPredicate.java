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

import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;
import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;

import java.util.function.Predicate;

/**
 * Abstract base implementation of a {@link FieldPredicate} for String Fields.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @since  2.2.0
 */
abstract class AbstractStringPredicate<ENTITY, D>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, D, String>>
implements HasArg0<String> {

    private final String operand;

    AbstractStringPredicate(PredicateType predicateType,
            final HasReferenceValue<ENTITY, D, String> field,
            final String operand,
            final Predicate<ENTITY> tester) {

        super(predicateType, field, tester);
        this.operand = operand;
    }

    @Override
    public final String get0() {
        return operand;
    }
}
