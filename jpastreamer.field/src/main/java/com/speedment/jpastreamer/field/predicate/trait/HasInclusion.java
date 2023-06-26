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
package com.speedment.jpastreamer.field.predicate.trait;

import com.speedment.jpastreamer.field.predicate.Inclusion;

/**
 * A common interface for predicates that test if an item is located
 * between two other items. This is useful for determining which 
 * inclusion strategy is expected from the stream.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasInclusion {
    
    /**
     * Returns the inclusion strategy used in the predicate.
     * 
     * @return  the inclusion strategy
     */
    Inclusion getInclusion();
    
}
