package com.speedment.jpastreamer.application;

import javax.persistence.EntityManagerFactory;

public interface JpaStreamerBuilder {

    JpaStreamerBuilder withPersistenceUnitName(String persistenceUnitName);

    JpaStreamerBuilder withEntityManagerFactory(EntityManagerFactory entityManagerFactory);

    JpaStreamer build();
}
