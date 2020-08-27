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
package com.speedment.jpastreamer.field.trait;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.method.ReferenceGetter;

/**
 * A representation of an Entity field that is a reference type (eg 
 * {@code Integer} and not {@code int}).
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the field value type
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public interface HasReferenceValue<ENTITY, V> extends Field<ENTITY> {

    @Override
    ReferenceGetter<ENTITY, V> getter();

    /**
     * Gets the value form the Entity field.
     *
     * @param e entity
     * @return the field value
     */
    default V get(ENTITY e) {
        return getter().apply(e);
    }

}