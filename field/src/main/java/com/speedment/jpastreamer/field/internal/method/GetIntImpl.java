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
package com.speedment.jpastreamer.field.internal.method;

import com.speedment.jpastreamer.field.method.IntGetter;
import com.speedment.jpastreamer.field.method.GetInt;
import com.speedment.jpastreamer.field.trait.HasIntValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetInt}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetIntImpl<ENTITY, D> implements GetInt<ENTITY> {
    
    private final HasIntValue<ENTITY> field;
    private final IntGetter<ENTITY> getter;
    
    public GetIntImpl(HasIntValue<ENTITY> field, IntGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasIntValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public int applyAsInt(ENTITY instance) {
        return getter.applyAsInt(instance);
    }
}