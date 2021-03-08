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
package com.speedment.jpastreamer.field.collector;

import com.speedment.jpastreamer.field.Field;

import java.util.stream.Collector;

/**
 *
 * @param <T>  the entity type to be collected
 * @param <A>  the intermediate accumulation type of the downstream collector
 * @param <R>  the collected result
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface FieldCollector<T, A, R> extends Collector<T, A, R> {

    /**
     * Returns the field that this collector is associated with.
     * 
     * @return  the field
     */
    Field<T> getField();
    
}
