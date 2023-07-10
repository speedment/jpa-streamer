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

import com.speedment.jpastreamer.field.method.BooleanGetter;
import com.speedment.jpastreamer.field.method.GetBoolean;
import com.speedment.jpastreamer.field.trait.HasBooleanValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetBoolean}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetBooleanImpl<ENTITY> implements GetBoolean<ENTITY> {
    
    private final HasBooleanValue<ENTITY> field;
    private final BooleanGetter<ENTITY> getter;
    
    public GetBooleanImpl(HasBooleanValue<ENTITY> field, BooleanGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasBooleanValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public boolean applyAsBoolean(ENTITY instance) {
        return getter.applyAsBoolean(instance);
    }
}
