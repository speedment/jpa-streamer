/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface Criteria<ENTITY, RETURN> {

    /**
     * Returns the {@code CriteriaBuilder} that is stored within this {@code Criteria}
     *
     * @return the {@code CriteriaBuilder} that is stored within this {@code Criteria}
     */
    CriteriaBuilder getBuilder();

    /**
     * Returns the {@code CriteriaQuery} that is stored within this {@code Criteria}
     *
     * @return the {@code CriteriaQuery} that is stored within this {@code Criteria}
     */
    CriteriaQuery<RETURN> getQuery();

    /**
     * Returns the {@code QueryParameters} that is stored within this {@code Criteria}
     *
     * @return the {@code QueryParameters} that is stored within this {@code Criteria}
     */
    List<QueryParameter> getQueryParameters();

    /**
     * Adds a {@code QueryParameter} to the current {@code Criteria}
     *
     * @param queryParameter to add
     * @param <T> parameter type
     */
    <T> void addQueryParameter(QueryParameter<T> queryParameter);

    /**
     * Returns the {@code Root} that is stored within this {@code Criteria}
     *
     * @return the {@code Root} that is stored within this {@code Criteria}
     */
    Root<ENTITY> getRoot();
}
