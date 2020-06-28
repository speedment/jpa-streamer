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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapper;
import com.speedment.jpastreamer.criteria.standard.support.CombinedStringPredicate;
import com.speedment.jpastreamer.criteria.standard.support.StringEqualPredicate;
import com.speedment.jpastreamer.exception.JpaStreamerException;
import com.speedment.jpastreamer.field.predicate.CombinedPredicate.Type;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.Collections;

final class InternalPredicateFactoryTest {

    private final PredicateFactory predicateFactory = new InternalPredicateFactory();
    private final PredicateMapper predicateMapper = new MockPredicateMapper();

    @BeforeEach
    public void setup() throws NoSuchFieldException {
        FieldSetter.setField(predicateFactory, predicateFactory.getClass().getDeclaredField("predicateMapper"), predicateMapper);
    }

    @Test
    void createPredicate() {
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, null));

        final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(criteriaBuilder.and(any())).thenReturn(null);
        when(criteriaBuilder.or(any())).thenReturn(null);

        final Criteria<String> criteria = new InternalCriteria<>(criteriaBuilder, null, null);
        final FieldPredicate<String> stringEqual = new StringEqualPredicate("value");

        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(criteria, null));
        assertThrows(NullPointerException.class, () -> predicateFactory.createPredicate(null, stringEqual));

        final SpeedmentPredicate<String> stringEqualPredicate = new StringEqualPredicate("value");
        final SpeedmentPredicate<String> stringNotEqualPredicate = stringEqualPredicate.negate();

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, stringEqualPredicate));

        final SpeedmentPredicate<String> invalidCombinedPredicate = new CombinedStringPredicate(Collections.singletonList(x -> true));

        assertThrows(JpaStreamerException.class, () -> predicateFactory.createPredicate(criteria, invalidCombinedPredicate));

        final SpeedmentPredicate<String> andCombinedPredicate = new CombinedStringPredicate(Arrays.asList(stringEqualPredicate, stringNotEqualPredicate));

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, andCombinedPredicate));

        final SpeedmentPredicate<String> orCombinedPredicate = new CombinedStringPredicate(Arrays.asList(stringEqualPredicate, stringNotEqualPredicate), Type.OR);

        assertDoesNotThrow(() -> predicateFactory.createPredicate(criteria, orCombinedPredicate));
    }

    private static final class MockPredicateMapper implements PredicateMapper {
        @Override
        public <T> Predicate mapPredicate(Criteria<T> criteria,
                FieldPredicate<T> fieldPredicate) {
            return null;
        }
    }
}
