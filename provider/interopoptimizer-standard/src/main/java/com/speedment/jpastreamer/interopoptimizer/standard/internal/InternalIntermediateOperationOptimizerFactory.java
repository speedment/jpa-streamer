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
package com.speedment.jpastreamer.interopoptimizer.standard.internal;

import static java.util.Objects.requireNonNull;

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
        requireNonNull(operationOptimizer);

        intermediateOperationOptimizers.get(Priority.NORMAL).add(operationOptimizer);
    }

    private void registerOptimizer(final IntermediateOperationOptimizer operationOptimizer, final Priority priority) {
        requireNonNull(operationOptimizer);
        requireNonNull(priority);

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
