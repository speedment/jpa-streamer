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

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.SORTED;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.OrderFactory;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.criteria.Order;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

public enum SortedCriteriaModifier implements CriteriaModifier {

    INSTANCE;

    private final OrderFactory orderFactory;

    SortedCriteriaModifier() {
        this.orderFactory = RootFactory.getOrThrow(OrderFactory.class, ServiceLoader::load);
    }

    @Override
    public <T> void modifyCriteria(
        final IntermediateOperationReference operationReference,
        final Criteria<T> criteria,
        final MergingTracker mergingTracker
    ) {
        requireNonNull(operationReference);
        requireNonNull(criteria);
        requireNonNull(mergingTracker);

        final IntermediateOperation<?, ?> operation = operationReference.get();

        final IntermediateOperationType operationType = operation.type();

        if (operationType != SORTED) {
            return;
        }

        final Optional<Comparator<T>> optionalComparator = getComparator(operation);

        if (optionalComparator.isPresent()) {
            final List<Order> orders;

            try {
                orders = orderFactory.createOrder(criteria, optionalComparator.get());
            } catch (JPAStreamerException e) {
                // don't merge operator in case we encounter an unsupported Comparator
                return;
            }

            final List<Order> previousOrders = new ArrayList<>(criteria.getQuery().getOrderList());
            previousOrders.addAll(orders);

            criteria.getQuery().orderBy(previousOrders);

            /*
            * If a Stream::sorted sequence contains a operation without a specified comparator
            * we cannot squash that sequence into a single operation. Because of this, we should
            * only mark the operation as merged if it's the last one in the sequence.
            * */
            operationReference.next().ifPresent(op -> {
                if (op.get().type() != SORTED) {
                    mergingTracker.markAsMerged(operationType);
                }
            });

            mergingTracker.markForRemoval(operationReference.index());
        } else {
            final EntityType<T> entityType = criteria.getRoot().getModel();

            entityType.getDeclaredSingularAttributes().stream()
                .filter(SingularAttribute::isId)
                .map(Attribute::getName)
                .findFirst().ifPresent(idFieldName -> {
                    final Order order = criteria.getBuilder()
                        .asc(criteria.getRoot().get(idFieldName));

                    final List<Order> previousOrders = new ArrayList<>(criteria.getQuery().getOrderList());
                    previousOrders.add(order);

                    criteria.getQuery().orderBy(previousOrders);

                    operationReference.next().ifPresent(op -> {
                        if (op.get().type() != SORTED) {
                            mergingTracker.markAsMerged(operationType);
                        }
                    });

                    mergingTracker.markForRemoval(operationReference.index());
            });
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<Comparator<T>> getComparator(final IntermediateOperation<?, ?> operation) {
        final Object[] arguments = operation.arguments();

        if (arguments.length != 1) {
            return Optional.empty();
        }

        if (arguments[0] instanceof Comparator) {
            return Optional.of((Comparator<T>) arguments[0]);
        }

        return Optional.empty();
    }
}
