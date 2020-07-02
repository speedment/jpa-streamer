package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface JPAStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void close();

    static JPAStreamerBuilder createJPAStreamerBuilder(final String persistenceUnitName) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(persistenceUnitName);
    }

    static JPAStreamerBuilder createJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(entityManagerFactory);
    }

}
