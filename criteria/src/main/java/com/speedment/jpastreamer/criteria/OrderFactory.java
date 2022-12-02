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
package com.speedment.jpastreamer.criteria;

import javax.persistence.criteria.Order;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface OrderFactory {

    /**
     * Creates and returns a JPA {@code Order} with the {@code Comparator} serving as the
     * model from which the {@code Order} is created. The JPA {@code Order} is created using
     * the provided {@code criteria}.
     *
     * @param criteria used to create the JPA Predicate
     * @param comparator used as a model for the JPA Order that is being created
     * @param <ENTITY> root entity
     * @return JPA Predicate
     */
    <ENTITY> List<Order> createOrder(
        final Criteria<ENTITY, ?> criteria,
        final Comparator<ENTITY> comparator
    );
}
