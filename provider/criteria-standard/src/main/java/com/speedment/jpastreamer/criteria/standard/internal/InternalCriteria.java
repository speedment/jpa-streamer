/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.criteria.standard.internal;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.QueryParameter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class InternalCriteria<ENTITY, RETURN> implements Criteria<ENTITY, RETURN> {

    private final CriteriaBuilder builder;
    private final CriteriaQuery<RETURN> query;
    private final Root<ENTITY> root;
    private final List<QueryParameter<?>> queryParameters;

    public InternalCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    ) {
        this.builder = builder;
        this.query = query;
        this.root = root;
        this.queryParameters = new ArrayList<>();
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
    public List<QueryParameter> getQueryParameters() {
        return unmodifiableList(queryParameters);
    }

    @Override
    public <T> void addQueryParameter(QueryParameter<T> queryParameter) {
        queryParameters.add(requireNonNull(queryParameter));
    }

    @Override
    public Root<ENTITY> getRoot() {
        return root;
    }
}
