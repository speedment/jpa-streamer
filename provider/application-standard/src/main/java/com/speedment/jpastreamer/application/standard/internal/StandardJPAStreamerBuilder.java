package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.application.JPAStreamerBuilder;

import javax.persistence.EntityManagerFactory;

import static java.util.Objects.requireNonNull;

public final class StandardJPAStreamerBuilder implements JPAStreamerBuilder {

    private final EntityManagerFactory entityManagerFactory;

    public StandardJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
    }

    @Override
    public JPAStreamer build() {
        return new StandardJPAStreamer(entityManagerFactory);
    }

}