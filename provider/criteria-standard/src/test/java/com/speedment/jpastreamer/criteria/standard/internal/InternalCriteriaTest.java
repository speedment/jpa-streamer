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

import com.speedment.jpastreamer.criteria.Criteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class InternalCriteriaTest {

    private final Criteria<?, ?> nullCriteria = new InternalCriteria<>(null, null, null);

    @Test
    void getBuilder() {
        Assertions.assertDoesNotThrow(nullCriteria::getBuilder);
    }

    @Test
    void getQuery() {
        Assertions.assertDoesNotThrow(nullCriteria::getQuery);
    }

    @Test
    void getRoot() {
        Assertions.assertDoesNotThrow(nullCriteria::getRoot);
    }
}
