/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
