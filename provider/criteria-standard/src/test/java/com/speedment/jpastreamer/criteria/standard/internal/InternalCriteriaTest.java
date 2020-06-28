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

package com.speedment.jpastreamer.criteria.standard.internal;

import com.speedment.jpastreamer.criteria.Criteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class InternalCriteriaTest {

    private final Criteria<Object> nullCriteria = new InternalCriteria<>(null, null, null);

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
