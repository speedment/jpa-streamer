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
package com.speedment.jpastreamer.field.predicate.trait;


import com.speedment.jpastreamer.field.Field;

/**
 * A trait for predicates the implement the {@link #getField()} method.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public interface HasField<ENTITY> {

    /**
     * Returns the {@link Field} that was used to generate this predicate.
     * 
     * @return  the field
     */
    Field<ENTITY> getField();
}
