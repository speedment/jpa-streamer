/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
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

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.CriteriaFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class InternalCriteriaFactory implements CriteriaFactory {

    @Override
    public <ENTITY, RETURN> Criteria<ENTITY, RETURN> createCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    ) {
        requireNonNull(builder);
        requireNonNull(query);
        requireNonNull(root);

        return new InternalCriteria<>(builder, query, root);
    }
}
