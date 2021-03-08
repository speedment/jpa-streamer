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
 * A short-cut functional reference to the {@code getXXX()} method for a
 * particular field in an entity.
 * <p>
 * A {@code ReferenceGetter<ENTITY, V>} has the following signature:
 * {@code
 *      interface ENTITY {
 *          V getXXX();
 *      }
 * }
 * 
 * @param <ENTITY>  the entity
 * @param <V>       the type of the value to return
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */

@FunctionalInterface
public interface ReferenceGetter<ENTITY, V> 
extends Getter<ENTITY>, Function<ENTITY, V> {
    
    @Override
    default Function<ENTITY, V> asFunction() {
        return this;
    }    

}
