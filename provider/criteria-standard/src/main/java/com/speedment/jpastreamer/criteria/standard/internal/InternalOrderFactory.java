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

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.OrderFactory;
import com.speedment.jpastreamer.criteria.standard.internal.order.OrderMapper;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.field.comparator.CombinedComparator;
import com.speedment.jpastreamer.field.comparator.FieldComparator;

import javax.persistence.criteria.Order;
import java.util.Comparator;
import java.util.List;

public final class InternalOrderFactory implements OrderFactory {

    private final OrderMapper orderMapper = OrderMapper.createOrderMapper();

    @Override
    public <ENTITY> List<Order> createOrder(
        final Criteria<ENTITY, ?> criteria,
        final Comparator<ENTITY> comparator
    ) {
        requireNonNull(criteria);
        requireNonNull(comparator);

        if (comparator instanceof FieldComparator) {
            final FieldComparator<ENTITY> fieldComparator = (FieldComparator<ENTITY>) comparator;

            return singletonList(orderMapper.mapOrder(criteria, fieldComparator));
        }

        if (comparator instanceof CombinedComparator) {
            final CombinedComparator<ENTITY> combinedComparator = (CombinedComparator<ENTITY>) comparator;

            return combinedComparator.stream()
                .map(fieldComparator -> orderMapper.mapOrder(criteria, fieldComparator))
                .collect(toList());
        }

        throw new JPAStreamerException(
            "Comparator type [" + comparator.getClass().getSimpleName() + "] is not supported"
        );
    }
}
