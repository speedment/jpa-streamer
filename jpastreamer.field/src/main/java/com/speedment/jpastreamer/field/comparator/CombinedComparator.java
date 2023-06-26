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
package com.speedment.jpastreamer.field.comparator;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * A combined {@link Comparator} that compares a number of
 * {@link FieldComparator FieldComparators} in sequence.
 *
 * @param <ENTITY>  the entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public interface CombinedComparator<ENTITY> extends Comparator<ENTITY> {

    @Override
    CombinedComparator<ENTITY> reversed();

    /**
     * Returns the comparators ordered so that the first comparator is
     * the most significant, and if that evaluates to {@code 0}, continue on to
     * the next one.
     *
     * @return  list of comparators
     */
    Stream<FieldComparator<? super ENTITY>> stream();

    /**
     * The number of comparators in the {@link #stream()}.
     *
     * @return  the number of comparators
     */
    int size();
}
