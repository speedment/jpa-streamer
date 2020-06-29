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

package com.speedment.jpastreamer.merger.standard.internal.query.strategy;

import com.speedment.jpastreamer.merger.standard.internal.reference.IntermediateOperationReference;
import com.speedment.jpastreamer.merger.standard.internal.tracker.MergingTracker;

import javax.persistence.Query;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface QueryModifier {

    /**
     * Modifies the provided {@code query} using the provided {@code operationReference} as
     * the source of the modification. If the modification is successful, the operation
     * type and the index of the operation must be stored in the {@code mergingTracker}
     * for later use.
     *
     * @param operationReference that provides modification information
     * @param query to modify
     * @param mergingTracker to store information about the side effects of the modification
     */
    void modifyQuery(
        final IntermediateOperationReference operationReference,
        final Query query,
        final MergingTracker mergingTracker
    );
}
