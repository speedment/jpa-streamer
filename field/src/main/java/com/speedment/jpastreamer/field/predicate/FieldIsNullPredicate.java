/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.field.predicate;

import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;

/**
 * Specialized {@link FieldPredicate} that also implements {@link IsNotNull}
 * from the {@code runtime-compute}-module.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface FieldIsNullPredicate<ENTITY, T>
extends FieldPredicate<ENTITY>, IsNull<ENTITY, T> {

    @Override
    boolean test(ENTITY value);

    @Override
    FieldIsNotNullPredicate<ENTITY, T> negate();

    @Override
    default PredicateType getPredicateType() {
        return PredicateType.IS_NULL;
    }

    @Override
    default boolean applyAsBoolean(ENTITY object) {
        return test(object);
    }
}
