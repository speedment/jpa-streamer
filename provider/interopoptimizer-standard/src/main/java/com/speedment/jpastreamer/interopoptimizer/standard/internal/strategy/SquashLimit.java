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

package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.abstracts.LongSquash;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class SquashLimit extends LongSquash {

    private final IntermediateOperationFactory intermediateOperationFactory;

    public SquashLimit(final IntermediateOperationFactory intermediateOperationFactory) {
        this.intermediateOperationFactory = intermediateOperationFactory;
    }

    @Override
    public IntermediateOperationType operationType() {
        return IntermediateOperationType.LIMIT;
    }

    @Override
    public Function<Long, IntermediateOperation<?, ?>> operationProvider() {
        return intermediateOperationFactory::createLimit;
    }

    @Override
    public Long initialValue() {
        return null;
    }

    @Override
    public BiFunction<Long, Long, Long> squash() {
        return (value, result) -> {
            if (result == null) {
                return value;
            }

            return Long.min(value, result);
        };
    }
}
