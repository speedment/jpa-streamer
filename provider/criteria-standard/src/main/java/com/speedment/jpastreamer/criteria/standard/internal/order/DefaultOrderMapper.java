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
