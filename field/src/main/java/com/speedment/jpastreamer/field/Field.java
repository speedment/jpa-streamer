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
package com.speedment.jpastreamer.field;

import com.speedment.jpastreamer.field.trait.HasColumnName;
import com.speedment.jpastreamer.field.trait.HasGetter;
import com.speedment.jpastreamer.field.trait.HasTable;

/**
 * The base interface for all fields.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public interface Field<ENTITY> extends
        HasColumnName,
        HasTable<ENTITY>,
        HasGetter<ENTITY> {
  
    /**
     * Returns {@code true} if the column that this field represents is UNIQUE.
     * 
     * @return  {@code true} if unique
     */
    boolean isUnique();

}