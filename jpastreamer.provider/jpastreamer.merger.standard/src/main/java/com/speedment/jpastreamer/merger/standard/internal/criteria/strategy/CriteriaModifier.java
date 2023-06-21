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
package com.speedment.jpastreamer.merger.standard.internal.criteria.strategy;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface CriteriaModifier {

    /**
     * Modifies the provided {@code criteria} using the provided {@code operationReference} as
     * the source of the modification. If the modification is successful, the operation
     * type and the index of the operation must be stored in the {@code mergingTracker}
     * for later use.
     *
     * @param operationReference that provides modification information
     * @param criteria to modify
     * @param mergingTracker to store information about the side effects of the modification
     * @param <ENTITY> entity type
     */
    <ENTITY> void modifyCriteria(
        final IntermediateOperationReference operationReference,
        final Criteria<ENTITY, ?> criteria,
        final MergingTracker mergingTracker
    );
}
