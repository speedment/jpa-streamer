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

import com.speedment.jpastreamer.field.method.DoubleGetter;
import com.speedment.jpastreamer.field.method.GetDouble;
import com.speedment.jpastreamer.field.trait.HasDoubleValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetDouble}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetDoubleImpl<ENTITY> implements GetDouble<ENTITY> {
    
    private final HasDoubleValue<ENTITY> field;
    private final DoubleGetter<ENTITY> getter;
    
    public GetDoubleImpl(HasDoubleValue<ENTITY> field, DoubleGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasDoubleValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public double applyAsDouble(ENTITY instance) {
        return getter.applyAsDouble(instance);
    }
}