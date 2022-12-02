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

        final Criteria<String, String> criteria = new InternalCriteria<>(null, null, null);
        final FieldPredicate<String> stringEqual = new StringEqualPredicate("value");

        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(criteria, null));
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, stringEqual));
    }

}
