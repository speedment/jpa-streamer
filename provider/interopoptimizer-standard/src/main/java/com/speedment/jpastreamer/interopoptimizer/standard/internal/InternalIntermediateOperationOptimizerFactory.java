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

package com.speedment.jpastreamer.interopoptimizer.standard.internal;

import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizer;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.RemoveOrderAffectingOperations;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashDistinct;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashFilter;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashLimit;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashSkip;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashSorted;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public final class InternalIntermediateOperationOptimizerFactory implements IntermediateOperationOptimizerFactory {

    private final Map<Priority, List<IntermediateOperationOptimizer>> intermediateOperationOptimizers = new EnumMap<>(Priority.class);

    public InternalIntermediateOperationOptimizerFactory() {
        final IntermediateOperationFactory intermediateOperationFactory = RootFactory
            .getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);

        intermediateOperationOptimizers.put(Priority.HIGHEST, new ArrayList<>());
        intermediateOperationOptimizers.put(Priority.HIGH, new ArrayList<>());
        intermediateOperationOptimizers.put(Priority.NORMAL, new ArrayList<>());
        intermediateOperationOptimizers.put(Priority.LOW, new ArrayList<>());
        intermediateOperationOptimizers.put(Priority.LOWEST, new ArrayList<>());

        registerOptimizer(new RemoveOrderAffectingOperations(), Priority.HIGH);
        registerOptimizer(new SquashSkip(intermediateOperationFactory));
        registerOptimizer(new SquashLimit(intermediateOperationFactory));
        registerOptimizer(new SquashFilter<>(intermediateOperationFactory));
        registerOptimizer(new SquashSorted<>(intermediateOperationFactory));
        registerOptimizer(new SquashDistinct(intermediateOperationFactory));
    }

    @Override
    public Stream<IntermediateOperationOptimizer> stream() {
        return intermediateOperationOptimizers.entrySet().stream()
            .sorted(Entry.comparingByKey(Priority::compareTo))
            .flatMap(x -> x.getValue().stream());
    }

    private void registerOptimizer(final IntermediateOperationOptimizer operationOptimizer) {
        Objects.requireNonNull(operationOptimizer);

        intermediateOperationOptimizers.get(Priority.NORMAL).add(operationOptimizer);
    }

    private void registerOptimizer(final IntermediateOperationOptimizer operationOptimizer, final Priority priority) {
        Objects.requireNonNull(operationOptimizer);
        Objects.requireNonNull(priority);

        intermediateOperationOptimizers.get(priority).add(operationOptimizer);
    }

    private enum Priority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST
    }

}
