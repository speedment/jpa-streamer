package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public final class InternalJpaStreamerBuilder implements JpaStreamerBuilder {

    private String persistenceUnitName;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public JpaStreamerBuilder withPersistenceUnitName(final String persistenceUnitName) {
        if (entityManagerFactory != null) {
            throw newIllegalStateException();
        }
        this.persistenceUnitName = requireNonNull(persistenceUnitName);
        return this;
    }

    @Override
    public JpaStreamerBuilder withEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
        if (persistenceUnitName != null) {
            throw newIllegalStateException();
        }
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
        return this;
    }

    @Override
    public JpaStreamer build() {
        if (persistenceUnitName == null && entityManagerFactory == null) {
            throw new IllegalStateException("Unable to build because neither withPersistenceUnitName nor withEntityManagerFactory was ever called.");
        }
        return new StandardJpaStreamer(
                Optional.ofNullable(entityManagerFactory)
                        .orElse(Persistence.createEntityManagerFactory(persistenceUnitName))
        );
    }

    private IllegalStateException newIllegalStateException() {
        return new IllegalStateException("Unable to accept configuration because withPersistenceUnitName and withEntityManagerFactory are mutually exclusive. Use either but not both.");
    }

}