/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.merger.standard.internal.tracker;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import java.util.HashSet;
import java.util.Set;

public final class DefaultMergingTracker implements MergingTracker {

    private final Set<IntermediateOperationType> mergedOperations = new HashSet<>();
    private final Set<Integer> forRemoval = new HashSet<>();

    @Override
    public Set<IntermediateOperationType> mergedOperations() {
        return unmodifiableSet(mergedOperations);
    }

    @Override
    public void markAsMerged(final IntermediateOperationType intermediateOperationType) {
        mergedOperations.add(requireNonNull(intermediateOperationType));
    }

    @Override
    public Set<Integer> forRemoval() {
        return unmodifiableSet(forRemoval);
    }

    @Override
    public void markForRemoval(int idx) {
        forRemoval.add(idx);
    }
}
