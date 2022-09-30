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
package com.speedment.jpastreamer.projection.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.projection.Projection;

import javax.persistence.Tuple;
import java.util.Collections;
import java.util.Set;

public final class InternalProjection<ENTITY> implements Projection<ENTITY> {

    private final Class<ENTITY> entityClass;
    private final Set<Field<ENTITY>> fields;
    private final TupleContext<ENTITY> tupleContext;

    public InternalProjection(final Class<ENTITY> entityClass, final Set<Field<ENTITY>> fields) {
        this.entityClass = requireNonNull(entityClass);
        this.fields = Collections.unmodifiableSet(fields);
        this.tupleContext = new TupleContext<>(entityClass, fields);
    }

    @Override
    public Class<ENTITY> entityClass() {
        return entityClass;
    }

    @Override
    public Set<Field<ENTITY>> fields() {
        return fields;
    }

    @Override
    public Tuple apply(ENTITY entity) {
        return tupleContext.create(entity);
    }
}
