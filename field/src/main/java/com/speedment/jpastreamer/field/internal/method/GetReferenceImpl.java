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

import com.speedment.jpastreamer.field.method.GetReference;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link GetReference}-interface.
 * 
 * @param <ENTITY> the entity type
 * @param <T>      the java type
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class GetReferenceImpl<ENTITY, T> implements GetReference<ENTITY, T> {

    private final HasReferenceValue<ENTITY, T> field;
    private final ReferenceGetter<ENTITY, T> getter;

    public GetReferenceImpl(HasReferenceValue<ENTITY, T> field, ReferenceGetter<ENTITY, T> getter) {
        this.field  = requireNonNull(field);
        this.getter = requireNonNull(getter);
    }

    @Override
    public HasReferenceValue<ENTITY, T> getField() {
        return field;
    }

    @Override
    public T apply(ENTITY instance) {
        return getter.apply(instance);
    }
}
