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

package com.speedment.jpastreamer.merger.standard.internal.reference;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.List;
import java.util.Optional;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface IntermediateOperationReference {

    /**
     * Returns the {@code IntermediateOperation} stored by this reference.
     *
     * @return {@code IntermediateOperation} stored by this reference
     */
    IntermediateOperation<?, ?> get();

    /**
     * Returns the index associated with the stored {@code IntermediateOperation}
     *
     * @return index of the stored {@code IntermediateOperation}
     */
    int index();

    /**
     * Returns an {@code Optional} which may contain a reference to the previous
     * {@code IntermediateOperation} in the {@code Pipeline}. If the current operation is
     * the first operation in the pipeline, an empty {@code Optional} will be returned.
     *
     * @return reference to the previous {@code IntermediateOperation} in the {@code Pipeline}
     */
    Optional<IntermediateOperationReference> previous();

    /**
     * Returns an {@code Optional} which may contain a reference to the next
     * {@code IntermediateOperation} in the {@code Pipeline}. If the current operation is
     * the last operation in the pipeline, an empty {@code Optional} will be returned.
     *
     * @return reference to the next {@code IntermediateOperation} in the {@code Pipeline}
     */
    Optional<IntermediateOperationReference> next();

    /**
     * Creates and returns a new {@code IntermediateOperationReference} from the provided
     * {@code operation}, {@code index} and {@code operations}.
     *
     * The provided {@code operations} are used to construct a path between the preceding
     * and subsequent intermediate operations.
     *
     * @param operation that is being referenced
     * @param index of the referenced operation
     * @param operations used to provide next/previous references
     * @return {@code IntermediateOperationReference} from the provided {@code operation},
     *         {@code index} and {@code operations}
     */
    static IntermediateOperationReference createReference(
        final IntermediateOperation<?, ?> operation,
        final int index,
        final List<IntermediateOperation<?, ?>> operations
    ) {
        return new DefaultIntermediateOperationReference(operation, index, operations);
    }
}
