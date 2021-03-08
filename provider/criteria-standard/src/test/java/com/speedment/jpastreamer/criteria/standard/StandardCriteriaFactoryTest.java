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
package com.speedment.jpastreamer.criteria.standard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.jpastreamer.criteria.CriteriaFactory;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

final class StandardCriteriaFactoryTest {

    final CriteriaFactory criteriaFactory = new StandardCriteriaFactory();

    @Test
    @SuppressWarnings("unchecked")
    void createCriteria() {
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, null, String.class));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, null));

        final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        final CriteriaQuery<Object> criteriaQuery = (CriteriaQuery<Object>) mock(CriteriaQuery.class);
        final Root<Object> root = (Root<Object>) mock(Root.class);

        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(criteriaBuilder, null, null));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, criteriaQuery, null));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, null, root));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(criteriaBuilder, criteriaQuery, null));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(criteriaBuilder, null, root));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, criteriaQuery, root));

        assertNotNull(criteriaFactory.createCriteria(criteriaBuilder, criteriaQuery, root));

        final EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Object.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Object.class)).thenReturn(root);

        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(entityManager, null));
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, Object.class));

        assertNotNull(criteriaFactory.createCriteria(entityManager, Object.class));
    }
}
