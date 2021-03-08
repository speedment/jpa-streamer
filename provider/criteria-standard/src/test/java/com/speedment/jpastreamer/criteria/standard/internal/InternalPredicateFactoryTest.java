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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapper;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapping;
import com.speedment.jpastreamer.criteria.standard.internal.util.ReflectionUtil;
import com.speedment.jpastreamer.criteria.standard.support.CombinedStringPredicate;
import com.speedment.jpastreamer.criteria.standard.support.StringEqualPredicate;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.field.predicate.CombinedPredicate.Type;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.Collections;

final class InternalPredicateFactoryTest {

    private final PredicateFactory predicateFactory = new InternalPredicateFactory();
    private final PredicateMapper predicateMapper = new MockPredicateMapper();

    @BeforeEach
    public void setup() throws NoSuchFieldException {
        ReflectionUtil.setField(predicateFactory, predicateFactory.getClass().getDeclaredField("predicateMapper"), predicateMapper);
    }

    @Test
    void createPredicate() {
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, null));

        final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(criteriaBuilder.and(any())).thenReturn(null);
        when(criteriaBuilder.or(any())).thenReturn(null);

        final Criteria<String, String> criteria = new InternalCriteria<>(criteriaBuilder, null, null);
        final FieldPredicate<String> stringEqual = new StringEqualPredicate("value");

        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(criteria, null));
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, stringEqual));

        final SpeedmentPredicate<String> stringEqualPredicate = new StringEqualPredicate("value");
        final SpeedmentPredicate<String> stringNotEqualPredicate = stringEqualPredicate.negate();

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, stringEqualPredicate));

        final SpeedmentPredicate<String> invalidCombinedPredicate = new CombinedStringPredicate(Collections.singletonList(x -> true));

        assertThrows(JPAStreamerException.class, () -> predicateFactory.createPredicate(criteria, invalidCombinedPredicate));

        final SpeedmentPredicate<String> andCombinedPredicate = new CombinedStringPredicate(Arrays.asList(stringEqualPredicate, stringNotEqualPredicate));

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, andCombinedPredicate));

        final SpeedmentPredicate<String> orCombinedPredicate = new CombinedStringPredicate(Arrays.asList(stringEqualPredicate, stringNotEqualPredicate), Type.OR);

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, orCombinedPredicate));
    }

    private static final class MockPredicateMapper implements PredicateMapper {
        @Override
        public <ENTITY> PredicateMapping mapPredicate(Criteria<ENTITY, ?> criteria,
                FieldPredicate<ENTITY> fieldPredicate) {
            return new PredicateMapping(null);
        }
    }
}
