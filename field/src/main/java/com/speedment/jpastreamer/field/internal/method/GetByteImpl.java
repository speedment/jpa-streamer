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

import com.speedment.jpastreamer.field.method.ByteGetter;
import com.speedment.jpastreamer.field.method.GetByte;
import com.speedment.jpastreamer.field.trait.HasByteValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetByte}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetByteImpl<ENTITY, D> implements GetByte<ENTITY> {
    
    private final HasByteValue<ENTITY> field;
    private final ByteGetter<ENTITY> getter;
    
    public GetByteImpl(HasByteValue<ENTITY> field, ByteGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasByteValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public byte applyAsByte(ENTITY instance) {
        return getter.applyAsByte(instance);
    }
}
