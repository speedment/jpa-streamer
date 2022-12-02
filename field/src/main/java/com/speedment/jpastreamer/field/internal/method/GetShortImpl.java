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

import com.speedment.jpastreamer.field.method.GetShort;
import com.speedment.jpastreamer.field.method.ShortGetter;
import com.speedment.jpastreamer.field.trait.HasShortValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetShort}-interface.
 * 
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetShortImpl<ENTITY> implements GetShort<ENTITY> {
    
    private final HasShortValue<ENTITY> field;
    private final ShortGetter<ENTITY> getter;
    
    public GetShortImpl(HasShortValue<ENTITY> field, ShortGetter<ENTITY> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }
    
    @Override
    public HasShortValue<ENTITY> getField() {
        return field;
    }
    
    @Override
    public short applyAsShort(ENTITY instance) {
        return getter.applyAsShort(instance);
    }
}
