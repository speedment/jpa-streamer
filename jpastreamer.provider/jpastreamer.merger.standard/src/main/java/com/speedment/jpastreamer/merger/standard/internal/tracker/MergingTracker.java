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
package com.speedment.jpastreamer.merger.standard.internal.tracker;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.Set;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface MergingTracker {

    /**
     * Returns a {@code Set} of {@code IntermediateOperationType} containing the
     * operations that were merged during the merge cycle. If the operations currently
     * being considered as a merge candidate is found within this set, it should not
     * be merged.
     *
     * @return {@code Set} of merged operations
     */
    Set<IntermediateOperationType> mergedOperations();

    /**
     * Marks an operation as merged, excluding it from the rest of the merging cycle.
     *
     * @param intermediateOperationType to be marked as merged
     */
    void markAsMerged(final IntermediateOperationType intermediateOperationType);

    /**
     * Returns a {@code Set} of indices that should be used as reference points to
     * operations that need to be removed from the pipeline after is merge cycle
     * is finished.
     *
     * @return {@code Set} of indices used as reference points to operations that need
     *         to be removed
     */
    Set<Integer> forRemoval();

    /**
     * Marks a specific operation for removal by storing its index in the pipeline.
     *
     * @param idx to be marked for removal
     */
    void markForRemoval(int idx);

    /**
     * Creates and returns a new {@code MergingTracker} instance.
     *
     * @return new {@code MergingTracker} instance
     */
    static MergingTracker createTracker() {
        return new DefaultMergingTracker();
    }
}
