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

import com.speedment.jpastreamer.field.internal.predicate.ComposedPredicateImpl;
import com.speedment.runtime.compute.expression.ComposedExpression;

/**
 * SpeedmentPredicate that composes a {@code Function} and another
 * {@code SpeedmentPredicate}, testing the output of the function with the
 * predicate to get the result.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface ComposedPredicate<ENTITY, A>
extends SpeedmentPredicate<ENTITY>,
        ComposedExpression<ENTITY, A> {

    SpeedmentPredicate<A> secondStep();

    @Override
    default SpeedmentPredicate<ENTITY> negate() {
        return new ComposedPredicateImpl<>(firstStep(), secondStep().negate());
    }
}
