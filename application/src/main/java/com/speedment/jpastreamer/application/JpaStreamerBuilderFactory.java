package com.speedment.jpastreamer.application;

import javax.persistence.EntityManagerFactory;

public interface JpaStreamerBuilderFactory {

    JpaStreamerBuilder create(String persistenceUnitName);

    JpaStreamerBuilder create(EntityManagerFactory entityManagerFactory);

}
