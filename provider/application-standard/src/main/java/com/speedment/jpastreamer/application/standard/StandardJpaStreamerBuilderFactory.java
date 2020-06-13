package com.speedment.jpastreamer.application.standard;

import com.speedment.jpastreamer.application.JpaStreamerBuilder;
import com.speedment.jpastreamer.application.JpaStreamerBuilderFactory;
import com.speedment.jpastreamer.application.standard.internal.StandardJpaStreamerBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static java.util.Objects.requireNonNull;

public final class StandardJpaStreamerBuilderFactory implements JpaStreamerBuilderFactory {

    @Override
    public JpaStreamerBuilder create(final String persistenceUnitName) {
        return create(Persistence.createEntityManagerFactory(requireNonNull(persistenceUnitName)));
    }

    @Override
    public JpaStreamerBuilder create(final EntityManagerFactory entityManagerFactory) {
        return new StandardJpaStreamerBuilder(requireNonNull(entityManagerFactory));
    }
}
