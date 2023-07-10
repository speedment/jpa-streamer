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
package com.speedment.jpastreamer.field.method;

import com.speedment.jpastreamer.field.trait.HasIntValue;

/**
 * A more detailed {@link IntGetter} that also contains information about the
 * field that created it.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface GetInt<ENTITY> extends IntGetter<ENTITY> {
    
    /**
     * Returns the field that created the {@code get()}-operation.
     * 
     * @return the field
     */
    HasIntValue<ENTITY> getField();
}
