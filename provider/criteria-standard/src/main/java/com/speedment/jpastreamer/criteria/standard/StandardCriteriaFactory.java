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
package com.speedment.jpastreamer.criteria.standard;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.CriteriaFactory;
import com.speedment.jpastreamer.criteria.standard.internal.InternalCriteriaFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class StandardCriteriaFactory implements CriteriaFactory {

    private final CriteriaFactory delegate = new InternalCriteriaFactory();

    @Override
    public <ENTITY, RETURN> Criteria<ENTITY, RETURN> createCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    ) {
        return delegate.createCriteria(builder, query, root);
    }

    @Override
    public <ENTITY> Criteria<ENTITY, ENTITY> createCriteria(
        final EntityManager entityManager,
        final Class<ENTITY> entityClass
    ) {
        return delegate.createCriteria(entityManager, entityClass);
    }
}
