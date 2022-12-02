/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.application.JPAStreamerBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class StandardJPAStreamerBuilder implements JPAStreamerBuilder {

    private final Supplier<EntityManager> entityManagerSupplier;
    
    private final Runnable closeHandler;
    
    private final boolean demoMode;

    private final boolean closeEntityManagers;

    public StandardJPAStreamerBuilder(final String persistenceUnitName) {
        requireNonNull(persistenceUnitName);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManagerSupplier = entityManagerFactory::createEntityManager;
        this.closeHandler = entityManagerFactory::close; 
        this.demoMode = "sakila".equals(persistenceUnitName); 
        this.closeEntityManagers = true; 
    }

    public StandardJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        requireNonNull(entityManagerFactory);
        this.entityManagerSupplier = entityManagerFactory::createEntityManager;
        this.closeHandler = () -> {}; 
        this.demoMode = "sakila".equals(entityManagerFactory.getProperties().getOrDefault("hibernate.ejb.persistenceUnitName", ""));
        this.closeEntityManagers = true; 
    }

    public StandardJPAStreamerBuilder(final Supplier<EntityManager> entityManagerSupplier) {
        this.entityManagerSupplier = requireNonNull(entityManagerSupplier);
        this.closeHandler = () -> {};
        this.demoMode = false; 
        this.closeEntityManagers = false; 
    }

    @Override
    public JPAStreamer build() {
        return new StandardJPAStreamer(entityManagerSupplier, closeHandler, demoMode, closeEntityManagers);
    }

}
