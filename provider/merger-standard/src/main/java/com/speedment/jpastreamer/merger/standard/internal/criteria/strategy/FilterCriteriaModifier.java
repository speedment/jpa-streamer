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
package com.speedment.jpastreamer.merger.standard.internal.criteria.strategy;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.*;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.criteria.Predicate;
import java.util.Optional;
import java.util.ServiceLoader;

public enum FilterCriteriaModifier implements CriteriaModifier {

    INSTANCE;

    private final PredicateFactory predicateFactory;

    FilterCriteriaModifier() {
        this.predicateFactory = RootFactory.getOrThrow(PredicateFactory.class, ServiceLoader::load);
    }

    @Override
    public <ENTITY> void modifyCriteria(
        final IntermediateOperationReference operationReference,
        final Criteria<ENTITY, ?> criteria,
        final MergingTracker mergingTracker
    ) {
        requireNonNull(operationReference);
        requireNonNull(criteria);
        requireNonNull(mergingTracker);

        final IntermediateOperation<?, ?> operation = operationReference.get();

        final IntermediateOperationType operationType = operation.type();

        if (operationType != FILTER) {
            return;
        }

        this.<ENTITY>getPredicate(operation).ifPresent(speedmentPredicate -> {
            final Predicate predicate = predicateFactory.createPredicate(criteria, speedmentPredicate);
            criteria.getQuery().where(predicate);

            mergingTracker.markAsMerged(operationType);
            mergingTracker.markForRemoval(operationReference.index());
        });
    }
    @SuppressWarnings("unchecked")
    private <T> Optional<SpeedmentPredicate<T>> getPredicate(final IntermediateOperation<?, ?> operation) {
        final Object[] arguments = operation.arguments();

        if (arguments.length != 1) {
            return Optional.empty();
        }

        if (arguments[0] instanceof SpeedmentPredicate) {
            return Optional.of((SpeedmentPredicate<T>) arguments[0]);
        }

        return Optional.empty();
    }
}
