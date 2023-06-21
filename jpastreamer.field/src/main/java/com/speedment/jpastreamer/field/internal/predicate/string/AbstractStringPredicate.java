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
abstract class AbstractStringPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, String>>
implements HasArg0<String> {

    private final String operand;

    AbstractStringPredicate(PredicateType predicateType,
            final HasReferenceValue<ENTITY, String> field,
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
