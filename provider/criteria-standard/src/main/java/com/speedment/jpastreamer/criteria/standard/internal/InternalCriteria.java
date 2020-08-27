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
package com.speedment.jpastreamer.criteria.standard.internal;

import com.speedment.jpastreamer.criteria.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class InternalCriteria<ENTITY, RETURN> implements Criteria<ENTITY, RETURN> {

    private final CriteriaBuilder builder;
    private final CriteriaQuery<RETURN> query;
    private final Root<ENTITY> root;

    public InternalCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    ) {
        this.builder = builder;
        this.query = query;
        this.root = root;
    }

    @Override
    public CriteriaBuilder getBuilder() {
        return builder;
    }

    @Override
    public CriteriaQuery<RETURN> getQuery() {
        return query;
    }

    @Override
    public Root<ENTITY> getRoot() {
        return root;
    }
}
