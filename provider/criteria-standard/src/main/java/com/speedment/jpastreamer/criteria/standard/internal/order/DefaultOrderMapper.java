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

package com.speedment.jpastreamer.criteria.standard.internal.order;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.comparator.FieldComparator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public final class DefaultOrderMapper implements OrderMapper {

    @Override
    public <ENTITY> Order mapOrder(
        final Criteria<ENTITY, ?> criteria,
        final FieldComparator<? super ENTITY> fieldComparator
    ) {
        requireNonNull(criteria);
        requireNonNull(fieldComparator);

        final Field<? super ENTITY> field = fieldComparator.getField();

        final CriteriaBuilder builder = criteria.getBuilder();
        final Root<ENTITY> root = criteria.getRoot();

        if (fieldComparator.isReversed()) {
            return builder.desc(root.get(field.columnName()));
        }

        return builder.asc(root.get(field.columnName()));
    }
}
