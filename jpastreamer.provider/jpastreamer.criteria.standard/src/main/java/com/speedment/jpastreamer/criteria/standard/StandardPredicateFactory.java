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
package com.speedment.jpastreamer.criteria.standard;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.PredicateFactory;
import com.speedment.jpastreamer.criteria.standard.internal.InternalPredicateFactory;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import jakarta.persistence.criteria.Predicate;

public final class StandardPredicateFactory implements PredicateFactory {

    private final PredicateFactory delegate = new InternalPredicateFactory();

    @Override
    public <ENTITY> Predicate createPredicate(
        final Criteria<ENTITY, ?> criteria,
        final SpeedmentPredicate<ENTITY> speedmentPredicate
    ) {
        return delegate.createPredicate(criteria, speedmentPredicate);
    }
}
