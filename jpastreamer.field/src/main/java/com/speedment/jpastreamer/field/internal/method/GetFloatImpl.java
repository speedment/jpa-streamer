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
package com.speedment.jpastreamer.field.internal.method;

import com.speedment.jpastreamer.field.method.FloatGetter;
import com.speedment.jpastreamer.field.method.GetFloat;
import com.speedment.jpastreamer.field.trait.HasFloatValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetFloat}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetFloatImpl<ENTITY> implements GetFloat<ENTITY> {
    
    private final HasFloatValue<ENTITY> field;
    private final FloatGetter<ENTITY> getter;
    
    public GetFloatImpl(HasFloatValue<ENTITY> field, FloatGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasFloatValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public float applyAsFloat(ENTITY instance) {
        return getter.applyAsFloat(instance);
    }
}
