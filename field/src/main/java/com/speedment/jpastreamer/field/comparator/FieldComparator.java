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
package com.speedment.jpastreamer.field.comparator;

import com.speedment.jpastreamer.field.Field;

import java.util.Comparator;

/**
 * A specialized {@link Comparator} that contains meta data information about
 * the field that is being compared.
 * <p>
 * Implementations of this interface are immutable. Equality is based upon the
 * equality of the {@link #getField() field}-identifier, the 
 * {@link #getNullOrder() null strategy} and the {@link #isReversed() order}.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface FieldComparator<ENTITY>
extends Comparator<ENTITY> {

    /**
     * Returns the field that created this comparator.
     * 
     * @return  the field
     */
    Field<ENTITY> getField();
    
    /**
     * Returns the strategy used when {@code null} values are encountered. If
     * the order {@link NullOrder#NONE} is specified, then no guarantees will be
     * made regarding the order of entities where the field value is 
     * {@code null}.
     * 
     * @return  the null order strategy
     */
    NullOrder getNullOrder();
    
    /**
     * Returns {@code true} if this comparator reverses the natural order of the
     * values in the current field. A reversed order descends from high values
     * to low.
     * 
     * @return  {@code true} if reversed (descending), else {@code false}
     */
    boolean isReversed();
    
    /**
     * Returns a new {@code FieldComparator} that order entities in the opposite
     * orders compared to this comparator. For an example, if this comparator
     * orders entities based on a column 'firstname' in descending order, then
     * the returned {@code FieldComparator} will be ordering entities based on
     * 'firstname' in ascending order.
     * 
     * @return  a new reverse comparator
     */
    @Override
    FieldComparator<ENTITY> reversed();
}
