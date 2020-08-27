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
import com.speedment.runtime.compute.ToBoolean;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Common interface for all metadata-rich predicates in the Speedment library.
 * Speedment predicates typically operates on an entity from a manager generated
 * by Speedment.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public interface SpeedmentPredicate<ENTITY> extends ToBoolean<ENTITY> {

    @Override
    default <T> ComposedPredicate<T, ENTITY>
    compose(Function<? super T, ? extends ENTITY> before) {
        return new ComposedPredicateImpl<>(before, this);
    }

    @Override
    default SpeedmentPredicate<ENTITY> negate() {
        return t -> !test(t);
    }

    @Override
    default SpeedmentPredicate<ENTITY> and(Predicate<? super ENTITY> other) {
        return CombinedPredicate.and(this, other);
    }

    @Override
    default SpeedmentPredicate<ENTITY> or(Predicate<? super ENTITY> other) {
        return CombinedPredicate.or(this, other);
    }
}