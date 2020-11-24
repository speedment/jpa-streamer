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

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import javax.persistence.criteria.JoinType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class StandardStreamConfiguration<T> implements StreamConfiguration<T> {

    private final Class<T> entityClass;
    private final Projection<T> projection;
    private final Set<JoinConfiguration<T>> joinConfigurations;

    public StandardStreamConfiguration(final Class<T> entityClass) {
        this.entityClass = requireNonNull(entityClass);
        this.projection = null;
        this.joinConfigurations = Collections.emptySet();
    }

    private StandardStreamConfiguration(final Class<T> entityClass, Projection<T> projection, final Set<JoinConfiguration<T>> joinConfigurations) {
        this.entityClass = entityClass;
        this.projection = projection;
        this.joinConfigurations = new HashSet<>(joinConfigurations);
    }

    @Override
    public Class<T> entityClass() {
        return entityClass;
    }

    @Override
    public Set<JoinConfiguration<T>> joins() {
        return Collections.unmodifiableSet(joinConfigurations);
    }

    @Override
    public StreamConfiguration<T> joining(final Field<T> field, final JoinType joinType) {
        requireNonNull(field);
        requireNonNull(joinType);
        final Set<JoinConfiguration<T>> newjoins = new HashSet<>(joinConfigurations);
        newjoins.add(new StandardJoinConfiguration<>(field, joinType));
        return new StandardStreamConfiguration<>(entityClass, projection, newjoins);
    }

    @Override
    public Optional<Projection<T>> select() {
        return Optional.ofNullable(projection);
    }

    @Override
    public StreamConfiguration<T> select(Projection<T> projection) {
        requireNonNull(projection);
        return new StandardStreamConfiguration<>(entityClass, projection, joinConfigurations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StandardStreamConfiguration<?> that = (StandardStreamConfiguration<?>) o;

        if (!entityClass.equals(that.entityClass)) return false;
        return joinConfigurations.equals(that.joinConfigurations);
    }

    @Override
    public int hashCode() {
        int result = entityClass.hashCode();
        result = 31 * result + joinConfigurations.hashCode();
        return result;
    }

    @Override
    public String toString() {

        final String joinText = joinConfigurations.isEmpty()
                ? ""
                : " joining " + joinConfigurations.stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.joining(", "));

        return "StandardStreamConfiguration{" +
                "of " + entityClass.getSimpleName() +
                joinText +
                '}';
    }
}
