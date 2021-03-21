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
package com.speedment.jpastreamer.merger.standard.internal.reference;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.List;
import java.util.Optional;

public final class DefaultIntermediateOperationReference implements IntermediateOperationReference {

    private final IntermediateOperation<?, ?> intermediateOperation;
    private final int index;

    private final List<IntermediateOperation<?, ?>> intermediateOperations;

    public DefaultIntermediateOperationReference(
        final IntermediateOperation<?, ?> intermediateOperation,
        int index,
        final List<IntermediateOperation<?, ?>> intermediateOperations
    ) {
        this.intermediateOperation = requireNonNull(intermediateOperation);
        this.index = index;
        this.intermediateOperations = unmodifiableList(requireNonNull(intermediateOperations));
    }

    @Override
    public IntermediateOperation<?, ?> get() {
        return intermediateOperation;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public Optional<IntermediateOperationReference> previous() {
        if (index - 1 < 0) {
            return Optional.empty();
        }

        final IntermediateOperation<?, ?> operation = intermediateOperations.get(index - 1);

        final IntermediateOperationReference reference = new DefaultIntermediateOperationReference(
            operation,
            index - 1,
            intermediateOperations
        );

        return Optional.of(reference);
    }

    @Override
    public Optional<IntermediateOperationReference> next() {
        if (index + 1 > intermediateOperations.size() - 1) {
            return Optional.empty();
        }

        final IntermediateOperation<?, ?> operation = intermediateOperations.get(index + 1);

        final IntermediateOperationReference reference = new DefaultIntermediateOperationReference(
            operation,
            index + 1,
            intermediateOperations
        );

        return Optional.of(reference);
    }
}
