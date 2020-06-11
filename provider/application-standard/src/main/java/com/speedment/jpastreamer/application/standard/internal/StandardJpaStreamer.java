package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.Streamer;

import static java.util.Objects.requireNonNull;

public final class StandardJpaStreamer implements JpaStreamer {

    private final String persistenceUnitName;

    public StandardJpaStreamer(final String persistenceUnitName) {
        this.persistenceUnitName = requireNonNull(persistenceUnitName);
    }

    @Override
    public <T> Streamer<T> streamer(Class<T> entityClass) {
        return new StandardStreamer<>(entityClass, persistenceUnitName);
    }
}