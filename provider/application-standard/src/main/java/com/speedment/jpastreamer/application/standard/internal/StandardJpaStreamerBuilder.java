package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;

import javax.persistence.EntityManagerFactory;

import static java.util.Objects.requireNonNull;

public final class StandardJpaStreamerBuilder implements JpaStreamerBuilder {

    private final EntityManagerFactory entityManagerFactory;

    public StandardJpaStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
    }

    @Override
    public JpaStreamer build() {
        return new StandardJpaStreamer(entityManagerFactory);
    }

}