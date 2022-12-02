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
package com.speedment.jpastreamer.projection;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.projection.internal.InternalProjection;

import javax.persistence.Tuple;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@code Projection} class represents a SQL projection.
 * Specific fields can be selected by using {@code Projection#select}.
 * The result of {@code Projection#select} can be passed to {@code
 * JPAStreamer#stream} to apply the specified projection.
 *
 * @author Mislav Milicevic
 */
public interface Projection<ENTITY> extends Function<ENTITY, Tuple> {

    /**
     * Returns the entity class that the projection is applied to.
     *
     * @return the entity class that the projection is applied to.
     */
    Class<ENTITY> entityClass();

    /**
     * Returns the fields selected by this projections.
     *
     * @return the fields selected by this projection.
     */
    Set<Field<ENTITY>> fields();

    @SafeVarargs
    @SuppressWarnings("varargs")
    static <ENTITY> Projection<ENTITY> select(final Field<ENTITY> first, final Field<ENTITY>... other) {
        requireNonNull(first);

        final Class<ENTITY> entityClass = first.table();
        final Set<Field<ENTITY>> fields = new LinkedHashSet<>();

        fields.add(first);

        if (other != null) {
            Collections.addAll(fields, other);
        }

        return new InternalProjection<>(entityClass, fields);
    }
}
