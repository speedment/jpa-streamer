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

import javax.persistence.AttributeConverter;

/**
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
public interface HasAttributeConverterClass<V, D> {
    
    /**
     * Returns the attribute converter class used by this field.
     * 
     * @return  the attribute converter class
     */
    Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass();
    
}
