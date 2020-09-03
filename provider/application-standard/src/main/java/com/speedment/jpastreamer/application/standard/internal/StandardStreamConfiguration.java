package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.StreamConfiguration;
import com.speedment.jpastreamer.field.Field;

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