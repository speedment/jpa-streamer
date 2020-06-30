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

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.List;
import java.util.Optional;

public final class DefaultIntermediateOperationReference implements IntermediateOperationReference {

    private final IntermediateOperation<?, ?> intermediateOperation;
    private final int index;

    private final transient List<IntermediateOperation<?, ?>> intermediateOperations;

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
