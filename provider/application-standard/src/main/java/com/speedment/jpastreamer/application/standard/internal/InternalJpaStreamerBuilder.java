package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;

import static java.util.Objects.requireNonNull;

public final class InternalJpaStreamerBuilder implements JpaStreamerBuilder {

    private String persistenceUnitName;

    @Override
    public JpaStreamerBuilder withPersistenceUnitName(final String persistenceUnitName) {
        this.persistenceUnitName = requireNonNull(persistenceUnitName);
        return this;
    }

    @Override
    public JpaStreamer build() {
        return new StandardJpaStreamer(persistenceUnitName);
    }
}