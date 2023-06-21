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

import static java.util.Objects.requireNonNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface CriteriaFactory {

    /**
     * Creates and returns a {@code Criteria} containing the provided {@code builder},
     * {@code query} and {@code root}.
     *
     * @param builder to store in the {@code Criteria}
     * @param query to store in the {@code Criteria}
     * @param root to store in the {@code Criteria}
     * @param <ENTITY> root entity
     * @param <RETURN> type returned by the query
     * @return a {@code Criteria} containing the provided {@code builder},
     *         {@code query} and {@code root}
     */
    <ENTITY, RETURN> Criteria<ENTITY, RETURN> createCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    );

    /**
     * Creates and returns a {@code Criteria} where the {@code CriteriaBuilder},
     * {@code CriteriaQuery} and {@code Root} are created by the provided {@code entityManager}.
     *
     * @param entityManager used to create the necessary {@code Criteria} objects
     * @param entityClass used to define the result model of the stored query and root
     * @param <ENTITY> root entity
     * @return a {@code Criteria}
     */
    default <ENTITY> Criteria<ENTITY, ENTITY> createCriteria(
        final EntityManager entityManager,
        final Class<ENTITY> entityClass
    ) {
        return createCriteria(entityManager, entityClass, entityClass);
    }

    /**
     * Creates and returns a {@code Criteria} where the {@code CriteriaBuilder},
     * {@code CriteriaQuery} and {@code Root} are created by the provided {@code entityManager}.
     *
     * @param entityManager used to create the necessary {@code Criteria} objects
     * @param entityClass used to define the result model of the root
     * @param returnClass used to define the result model of the query
     * @param <ENTITY> root entity
     * @param <RETURN> query return entity
     * @return a {@code Criteria}
     */
    default <ENTITY, RETURN> Criteria<ENTITY, RETURN> createCriteria(
        final EntityManager entityManager,
        final Class<ENTITY> entityClass,
        final Class<RETURN> returnClass
    ) {
        requireNonNull(entityManager);
        requireNonNull(entityClass);
        requireNonNull(returnClass);

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<RETURN> criteriaQuery = criteriaBuilder.createQuery(returnClass);
        final Root<ENTITY> root = criteriaQuery.from(entityClass);

        return createCriteria(criteriaBuilder, criteriaQuery, root);
    }
}
