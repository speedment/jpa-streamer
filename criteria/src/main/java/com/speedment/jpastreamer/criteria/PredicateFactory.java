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

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import javax.persistence.criteria.Predicate;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface PredicateFactory {

    /**
     * Creates and returns a JPA {@code Predicate} with the {@code SpeedmentPredicate} serving
     * as the model. The JPA {@code Predicate} is created using the provided {@code criteria}
     *
     * @param criteria used to create the JPA Predicate
     * @param speedmentPredicate used as a model for the JPA Predicate that is being created
     * @param <ENTITY> root entity used in the Speedment Predicate
     * @return JPA Predicate
     */
    <ENTITY> Predicate createPredicate(
        final Criteria<ENTITY, ?> criteria,
        final SpeedmentPredicate<ENTITY> speedmentPredicate
    );
}
