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
package com.speedment.jpastreamer.criteria.standard;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.OrderFactory;
import com.speedment.jpastreamer.criteria.standard.internal.InternalOrderFactory;

import javax.persistence.criteria.Order;
import java.util.Comparator;
import java.util.List;

public final class StandardOrderFactory implements OrderFactory {

    private final OrderFactory delegate = new InternalOrderFactory();

    @Override
    public <ENTITY> List<Order> createOrder(
        final Criteria<ENTITY, ?> criteria,
        final Comparator<ENTITY> comparator
    ) {
        return delegate.createOrder(criteria, comparator);
    }
}
