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
    public <T> List<Order> createOrder(
        final Criteria<T> criteria,
        final Comparator<T> comparator
    ) {
        return delegate.createOrder(criteria, comparator);
    }
}
