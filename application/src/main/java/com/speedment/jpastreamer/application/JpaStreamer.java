package com.speedment.jpastreamer.application;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface JpaStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void stop();

    static JpaStreamerBuilder createJpaStreamerBuilder(final String persistenceUnitName) {
        return ServiceLoader.load(JpaStreamerBuilderFactory.class).iterator().next().create(persistenceUnitName);
    }

    static JpaStreamerBuilder createJpaStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return ServiceLoader.load(JpaStreamerBuilderFactory.class).iterator().next().create(entityManagerFactory);
    }

}