/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.speedment.jpastreamer.criteria;

import static java.util.Objects.requireNonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
     * @param <T> root entity
     * @return a {@code Criteria} containing the provided {@code builder},
     *         {@code query} and {@code root}
     */
    <T> Criteria<T> createCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<T> query,
        final Root<T> root
    );

    /**
     * Creates and returns a {@code Criteria} where the {@code CriteriaBuilder},
     * {@code CriteriaQuery} and {@code Root} are created by the provided {@code entityManager}.
     *
     * @param entityManager used to create the necessary {@code Criteria} objects
     * @param entityClass used to define the result model of the stored query and root
     * @param <T> root entity
     * @return a {@code Criteria}
     */
    default <T> Criteria<T> createCriteria(
        final EntityManager entityManager,
        final Class<T> entityClass
    ) {
        requireNonNull(entityManager);
        requireNonNull(entityClass);

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<T> root = criteriaQuery.from(entityClass);

        return createCriteria(criteriaBuilder, criteriaQuery, root);
    }
}
