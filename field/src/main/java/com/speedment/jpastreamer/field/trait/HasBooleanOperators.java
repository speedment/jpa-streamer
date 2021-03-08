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

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

/**
 * A representation of an Entity field that is of boolean type
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @since  3.0.17
 */
public interface HasBooleanOperators<ENTITY> extends Field<ENTITY> {

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value
     */
    SpeedmentPredicate<ENTITY> equal(boolean value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value
     */
    SpeedmentPredicate<ENTITY> notEqual(boolean value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>true</em>
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>true</em>.
     */
    default SpeedmentPredicate<ENTITY> isTrue() {
        return equal(true);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>false</em>
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>false</em>.
     */
    default SpeedmentPredicate<ENTITY> isFalse() {
        return equal(false);
    }

}
