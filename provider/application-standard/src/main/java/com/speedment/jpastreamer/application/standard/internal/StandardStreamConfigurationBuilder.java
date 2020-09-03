package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.StreamConfiguration;
import com.speedment.jpastreamer.application.StreamConfigurationBuilder;
import com.speedment.jpastreamer.field.Field;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class StandardStreamConfigurationBuilder<T> implements StreamConfigurationBuilder<T> {

    private final Class<T> entityClass;
    private final Set<Field<T>> joins;

    public StandardStreamConfigurationBuilder(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.joins = new HashSet<>();
    }

    @Override
    public StreamConfigurationBuilder<T> joining(final Field<T> field) {
        joins.add(requireNonNull(field));
        return this;
    }

    @Override
    public StreamConfiguration<T> build() {
        return new StandardStreamConfiguration<>(entityClass, joins);
    }
}