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
package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public final class StandardStreamConfiguration<T> implements StreamConfiguration<T> {

    private final Class<T> entityClass;
    private final Set<Field<T>> joins;

    public StandardStreamConfiguration(final Class<T> entityClass) {
        this.entityClass = requireNonNull(entityClass);
        this.joins = Collections.emptySet();
    }

    private StandardStreamConfiguration(final Class<T> entityClass, final Set<Field<T>> joins) {
        this.entityClass = entityClass;
        this.joins = new HashSet<>(joins);
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
    public StreamConfiguration<T> joining(Field<T> field) {
        final Set<Field<T>> newjoins = new HashSet<>(joins);
        newjoins.add(field);
        return new StandardStreamConfiguration<>(entityClass, newjoins);
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
                "of " + entityClass.getSimpleName() +
                " joining " + joins.stream().map(Field::columnName).sorted().collect(Collectors.joining(", ")) +
                '}';
    }
}