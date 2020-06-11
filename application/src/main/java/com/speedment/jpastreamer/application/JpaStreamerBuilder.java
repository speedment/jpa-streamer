package com.speedment.jpastreamer.application;

public interface JpaStreamerBuilder {

    JpaStreamerBuilder withPersistenceUnitName(String persistenceUnitName);

    JpaStreamer build();
}
