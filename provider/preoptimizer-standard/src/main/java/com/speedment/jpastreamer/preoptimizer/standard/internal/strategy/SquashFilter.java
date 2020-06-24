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

package com.speedment.jpastreamer.preoptimizer.standard.internal.strategy;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;
import com.speedment.jpastreamer.preoptimizer.standard.internal.strategy.abstracts.AbstractPredicateSquash;

import java.util.function.Function;
import java.util.function.Predicate;

public final class SquashFilter extends AbstractPredicateSquash {

    private final IntermediateOperationFactory intermediateOperationFactory;

    public SquashFilter(final IntermediateOperationFactory intermediateOperationFactory) {
        this.intermediateOperationFactory = intermediateOperationFactory;
    }

    @Override
    protected IntermediateOperationType operationType() {
        return IntermediateOperationType.FILTER;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Function<Predicate, IntermediateOperation<?, ?>> operationProvider() {
        return intermediateOperationFactory::createFilter;
    }
}
