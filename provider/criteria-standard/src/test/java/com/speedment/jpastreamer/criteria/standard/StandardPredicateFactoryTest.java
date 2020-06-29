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

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.InternalCriteria;
import com.speedment.jpastreamer.criteria.standard.support.StringEqualPredicate;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import org.junit.jupiter.api.Test;

final class StandardPredicateFactoryTest {

    private final PredicateFactory predicateFactory = new StandardPredicateFactory();

    @Test
    void createPredicate() {
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, null));

        final Criteria<String> criteria = new InternalCriteria<>(null, null, null);
        final FieldPredicate<String> stringEqual = new StringEqualPredicate("value");

        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(criteria, null));
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, stringEqual));
    }

}
