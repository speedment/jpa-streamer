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

import java.util.function.Function;
import java.util.function.ToLongFunction;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code LongGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         long getXXX();
 *     }
 * }
 * 
 * @param <ENTITY> the entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@FunctionalInterface
public interface LongGetter<ENTITY> extends Getter<ENTITY>, ToLongFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    long applyAsLong(ENTITY instance);
    
    @Override
    default Long apply(ENTITY instance) {
        return applyAsLong(instance);
    }
    
    @Override
    default Function<ENTITY, Long> asFunction() {
        return this::apply;
    }
}
