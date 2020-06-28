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

package com.speedment.jpastreamer.criteria.standard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
        assertThrows(NullPointerException.class, () -> criteriaFactory.createCriteria(null, null, null));
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
