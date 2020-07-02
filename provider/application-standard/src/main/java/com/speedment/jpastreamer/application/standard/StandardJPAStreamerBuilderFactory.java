package com.speedment.jpastreamer.application.standard;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.application.JPAStreamerBuilder;
import com.speedment.jpastreamer.application.JPAStreamerBuilderFactory;
import com.speedment.jpastreamer.application.standard.internal.StandardJPAStreamerBuilder;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class StandardJPAStreamerBuilderFactory implements JPAStreamerBuilderFactory {

    @Override
    public JPAStreamerBuilder create(final String persistenceUnitName) {
        return create(Persistence.createEntityManagerFactory(requireNonNull(persistenceUnitName)));
    }

    @Override
    public JPAStreamerBuilder create(final EntityManagerFactory entityManagerFactory) {
        return new StandardJPAStreamerBuilder(requireNonNull(entityManagerFactory));
    }
}
