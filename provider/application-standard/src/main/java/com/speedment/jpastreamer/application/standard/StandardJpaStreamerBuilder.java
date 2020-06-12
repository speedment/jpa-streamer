package com.speedment.jpastreamer.application.standard;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;
import com.speedment.jpastreamer.application.standard.internal.InternalJpaStreamerBuilder;

import javax.persistence.EntityManagerFactory;

public final class StandardJpaStreamerBuilder implements JpaStreamerBuilder {

    private final JpaStreamerBuilder delegate = new InternalJpaStreamerBuilder();

    @Override
    public JpaStreamerBuilder withPersistenceUnitName(String persistenceUnitName) {
        return delegate.withPersistenceUnitName(persistenceUnitName);
    }

    @Override
    public JpaStreamerBuilder withEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        return delegate.withEntityManagerFactory(entityManagerFactory);
    }

    @Override
    public JpaStreamer build() {
        return delegate.build();
    }

}