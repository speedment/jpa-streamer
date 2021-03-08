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
package com.speedment.jpastreamer.field.internal.method;

import com.speedment.jpastreamer.field.method.CharGetter;
import com.speedment.jpastreamer.field.method.GetChar;
import com.speedment.jpastreamer.field.trait.HasCharValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetChar}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetCharImpl<ENTITY> implements GetChar<ENTITY> {
    
    private final HasCharValue<ENTITY> field;
    private final CharGetter<ENTITY> getter;
    
    public GetCharImpl(HasCharValue<ENTITY> field, CharGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasCharValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public char applyAsChar(ENTITY instance) {
        return getter.applyAsChar(instance);
    }
}
