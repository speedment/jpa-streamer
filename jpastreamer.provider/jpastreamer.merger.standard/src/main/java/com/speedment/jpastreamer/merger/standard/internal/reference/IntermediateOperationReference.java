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
