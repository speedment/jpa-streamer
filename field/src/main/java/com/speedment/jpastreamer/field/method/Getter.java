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
package com.speedment.jpastreamer.field.method;

import java.util.function.Function;

/**
 *
 * @param  <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface Getter<ENTITY> {
    
    /**
     * A generic (untyped) get method.
     * 
     * @param entity  the entity to get from
     * @return        the value
     */
    Object apply(ENTITY entity);
    
    /**
     * Returns this object, typed as a {@code Function} method.
     * 
     * @return  this object as a function
     */
    default Function<ENTITY, ? extends Object> asFunction() {
        return this::apply;
    }
}
