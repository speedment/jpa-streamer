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

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapper;
import com.speedment.jpastreamer.exception.JpaStreamerException;
import com.speedment.jpastreamer.field.predicate.CombinedPredicate;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import javax.persistence.criteria.Predicate;

public final class InternalPredicateFactory implements PredicateFactory {

    private final PredicateMapper predicateMapper = PredicateMapper.createPredicateMapper();

    @Override
    @SuppressWarnings("unchecked")
    public <T> Predicate createPredicate(
        final Criteria<T> criteria,
        final SpeedmentPredicate<T> speedmentPredicate
    ) {
        requireNonNull(criteria);
        requireNonNull(speedmentPredicate);

        if (speedmentPredicate instanceof FieldPredicate) {
            final FieldPredicate<T> fieldPredicate = (FieldPredicate<T>) speedmentPredicate;

            return predicateMapper.mapPredicate(criteria, fieldPredicate);
        }

        if (speedmentPredicate instanceof CombinedPredicate) {
            final CombinedPredicate<T> combinedPredicate = (CombinedPredicate<T>) speedmentPredicate;

            final Predicate[] predicates = combinedPredicate.stream().map(predicate -> {
                if (predicate instanceof SpeedmentPredicate) {
                    return createPredicate(criteria, (SpeedmentPredicate<T>) predicate);
                }
                throw new JpaStreamerException(
                    "Predicate type [" + predicate.getClass().getSimpleName() + "] is not supported"
                );
            }).toArray(Predicate[]::new);

            switch (combinedPredicate.getType()) {
                case AND:
                    return criteria.getBuilder().and(predicates);
                case OR:
                    return criteria.getBuilder().or(predicates);
                default:
                    throw new JpaStreamerException(
                        "Predicate logical operator [" + combinedPredicate.getType() + "] is not supported"
                    );
            }
        }

        throw new JpaStreamerException(
            "Predicate type [" + speedmentPredicate.getClass().getSimpleName() + "] is not supported"
        );
    }
}
