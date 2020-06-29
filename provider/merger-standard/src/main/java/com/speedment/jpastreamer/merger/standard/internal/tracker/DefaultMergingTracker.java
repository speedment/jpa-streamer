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
