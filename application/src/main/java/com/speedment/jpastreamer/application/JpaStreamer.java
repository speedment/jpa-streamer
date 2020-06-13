package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.serviceloader.ServiceLoaderUtil;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface JpaStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void stop();

    static JpaStreamerBuilder createJpaStreamerBuilder(final String persistenceUnitName) {
        return ServiceLoaderUtil
                .getOrThrow(JpaStreamerBuilderFactory.class)
                .create(persistenceUnitName);
    }

    static JpaStreamerBuilder createJpaStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return ServiceLoaderUtil
                .getOrThrow(JpaStreamerBuilderFactory.class)
                .create(entityManagerFactory);
    }

}