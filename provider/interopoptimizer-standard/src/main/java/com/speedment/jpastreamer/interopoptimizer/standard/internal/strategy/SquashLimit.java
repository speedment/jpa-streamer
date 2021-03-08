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
