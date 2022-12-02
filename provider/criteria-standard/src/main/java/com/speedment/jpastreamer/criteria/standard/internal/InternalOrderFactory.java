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
