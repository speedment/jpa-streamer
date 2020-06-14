package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManagerFactory;
import java.util.stream.Stream;

public interface JpaStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void stop();

    static JpaStreamerBuilder createJpaStreamerBuilder(final String persistenceUnitName) {
        return RootFactory
                .getOrThrow(JpaStreamerBuilderFactory.class)
                .create(persistenceUnitName);
    }

    static JpaStreamerBuilder createJpaStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return RootFactory
                .getOrThrow(JpaStreamerBuilderFactory.class)
                .create(entityManagerFactory);
    }

}