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
import static java.util.stream.Collectors.toList;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.OrderFactory;
import com.speedment.jpastreamer.criteria.standard.internal.order.OrderMapper;
import com.speedment.jpastreamer.exception.JpaStreamerException;
import com.speedment.jpastreamer.field.comparator.CombinedComparator;
import com.speedment.jpastreamer.field.comparator.FieldComparator;

import javax.persistence.criteria.Order;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class InternalOrderFactory implements OrderFactory {

    private final OrderMapper orderMapper = OrderMapper.createOrderMapper();

    @Override
    public <T> List<Order> createOrder(
        final Criteria<T> criteria,
        final Comparator<T> comparator
    ) {
        Objects.requireNonNull(criteria);
        Objects.requireNonNull(comparator);

        if (comparator instanceof FieldComparator) {
            final FieldComparator<T> fieldComparator = (FieldComparator<T>) comparator;

            return singletonList(orderMapper.mapOrder(criteria, fieldComparator));
        }

        if (comparator instanceof CombinedComparator) {
            final CombinedComparator<T> combinedComparator = (CombinedComparator<T>) comparator;

            return combinedComparator.stream()
                .map(fieldComparator -> orderMapper.mapOrder(criteria, fieldComparator))
                .collect(toList());
        }

        throw new JpaStreamerException(
            "Comparator type [" + comparator.getClass().getSimpleName() + "] is not supported"
        );
    }
}
