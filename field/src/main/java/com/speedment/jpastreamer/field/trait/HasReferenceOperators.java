/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.field.trait;

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasReferenceOperators<ENTITY> {
    
    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is {@code null}
     */
    SpeedmentPredicate<ENTITY> isNull();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not</em> {@code null}
     */
    default SpeedmentPredicate<ENTITY> isNotNull() {
        return isNull().negate();
    }
}
