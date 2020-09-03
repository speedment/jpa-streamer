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

package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class StandardStreamConfiguration<T> implements StreamConfiguration<T> {

    private final Class<T> entityClass;
    private final Set<Field<T>> joins;

    public StandardStreamConfiguration(final Class<T> entityClass, final Set<Field<T>> joins) {
        this.entityClass = requireNonNull(entityClass);
        this.joins = new HashSet<>(requireNonNull(joins));
    }

    @Override
    public Class<T> entityClass() {
        return entityClass;
    }

    @Override
    public Set<Field<T>> joins() {
        return Collections.unmodifiableSet(joins);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StandardStreamConfiguration<?> that = (StandardStreamConfiguration<?>) o;

        if (!entityClass.equals(that.entityClass)) return false;
        return joins.equals(that.joins);
    }

    @Override
    public int hashCode() {
        int result = entityClass.hashCode();
        result = 31 * result + joins.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StandardStreamConfiguration{" +
                "entityClass=" + entityClass +
                ", joins=" + joins +
                '}';
    }
}
