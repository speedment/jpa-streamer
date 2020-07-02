package com.speedment.jpastreamer.application;

import javax.persistence.EntityManagerFactory;

public interface JPAStreamerBuilderFactory {

    JPAStreamerBuilder create(String persistenceUnitName);

    JPAStreamerBuilder create(EntityManagerFactory entityManagerFactory);

}
