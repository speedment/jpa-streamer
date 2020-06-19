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

package com.speedment.jpastreamer.merger.standard.internal.criteria.strategy;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.CriteriaFactory;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.result.CriteriaMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.criteria.result.StandardCriteriaMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public enum FilterCriteriaMerger implements CriteriaMerger {

    INSTANCE;

    private final CriteriaFactory criteriaFactory;

    FilterCriteriaMerger() {
        this.criteriaFactory = RootFactory.getOrThrow(CriteriaFactory.class);
    }

    @Override
    public <T> CriteriaMergeResult<T> merge(
        final Pipeline<T> pipeline,
        final CriteriaQuery<T> criteriaQuery,
        final CriteriaBuilder criteriaBuilder
    ) {
        requireNonNull(pipeline);
        requireNonNull(criteriaQuery);
        requireNonNull(criteriaBuilder);

        final List<IntermediateOperation<?, ?>> operations = pipeline.intermediateOperations();

        Integer filterIndex = null;

        for (int i = 0; i < operations.size(); i++) {
            final IntermediateOperation<?, ?> operation = operations.get(i);

            if (operation.type() != IntermediateOperationType.FILTER) {
                continue;
            }

            final Optional<SpeedmentPredicate<T>> optionalPredicate = getPredicate(operation);

            if (optionalPredicate.isPresent()) {
                final Predicate predicate = criteriaFactory.createPredicate(criteriaBuilder, optionalPredicate.get());
                criteriaQuery.where(predicate);

                filterIndex = i;
            }

            break;
        }

        if (filterIndex != null) {
            operations.remove((int) filterIndex);
        }

        return new StandardCriteriaMergeResult<>(pipeline, criteriaQuery);
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
