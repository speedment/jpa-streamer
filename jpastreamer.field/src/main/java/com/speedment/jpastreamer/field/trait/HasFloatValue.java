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
package com.speedment.jpastreamer.field.trait;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.method.GetFloat;

/**
 * A representation of an Entity field that is a primitive {@code float} type.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public interface HasFloatValue<ENTITY> extends Field<ENTITY> {
    
    @Override
    GetFloat<ENTITY> getter();

    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default float getAsFloat(ENTITY entity) {
        return getter().applyAsFloat(entity);
    }

}
