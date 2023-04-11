/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.criteria.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapper;
import com.speedment.jpastreamer.criteria.standard.internal.predicate.PredicateMapping;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.field.predicate.CombinedPredicate;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import jakarta.persistence.criteria.Predicate;

public final class InternalPredicateFactory implements PredicateFactory {

    private final PredicateMapper predicateMapper = PredicateMapper.createPredicateMapper();

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> Predicate createPredicate(
        final Criteria<ENTITY, ?> criteria,
        final SpeedmentPredicate<ENTITY> speedmentPredicate
    ) {
        requireNonNull(criteria);
        requireNonNull(speedmentPredicate);

        if (speedmentPredicate instanceof FieldPredicate) {
            final FieldPredicate<ENTITY> fieldPredicate = (FieldPredicate<ENTITY>) speedmentPredicate;
            final PredicateMapping predicateMapping = predicateMapper.mapPredicate(criteria, fieldPredicate);

            predicateMapping.getQueryParameters().forEach(criteria::addQueryParameter);

            return predicateMapping.getPredicate();
        }

        if (speedmentPredicate instanceof CombinedPredicate) {
            final CombinedPredicate<ENTITY> combinedPredicate = (CombinedPredicate<ENTITY>) speedmentPredicate;

            final Predicate[] predicates = combinedPredicate.stream().map(predicate -> {
                if (predicate instanceof SpeedmentPredicate) {
                    return createPredicate(criteria, (SpeedmentPredicate<ENTITY>) predicate);
                }
                throw newJPAStreamerException("type", predicate.getClass().getSimpleName());

            }).toArray(Predicate[]::new);

            switch (combinedPredicate.getType()) {
                case AND:
                    return criteria.getBuilder().and(predicates);
                case OR:
                    return criteria.getBuilder().or(predicates);
                default:
                    throw newJPAStreamerException("logical operator", combinedPredicate.getType().toString());
            }
        }
        throw newJPAStreamerException("logical type", speedmentPredicate.getClass().getSimpleName());
    }

    private JPAStreamerException newJPAStreamerException(String item, String typeName) {
        return new JPAStreamerException(
                "Predicate " + item + " [" + typeName + "] is not supported"
        );
    }

}
