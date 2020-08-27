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
package com.speedment.jpastreamer.field.internal.predicate;

import com.speedment.jpastreamer.field.predicate.CombinedPredicate;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a Predicate that is used to build up higher orders
 * of predicates.
 *
 * @param <T>  the type being evaluated
 * 
 * @author  Per Minborg
 * @since   2.1.0
 */
abstract class AbstractPredicate<T> implements SpeedmentPredicate<T> {

    AbstractPredicate() {}

    @Override
    public SpeedmentPredicate<T> and(Predicate<? super T> other) {
        requireNonNull(other);
        return CombinedPredicate.and(this, other);
    }

    @Override
    public SpeedmentPredicate<T> or(Predicate<? super T> other) {
        requireNonNull(other);
        return CombinedPredicate.or(this, other);
    }
}