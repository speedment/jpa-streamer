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

import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashDistinct;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashFilter;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashLimit;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizer;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.SquashSkip;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public final class InternalIntermediateOperationOptimizerFactory implements IntermediateOperationOptimizerFactory {

    private final List<IntermediateOperationOptimizer> intermediateOperationOptimizers = new ArrayList<>();

    public InternalIntermediateOperationOptimizerFactory() {
        final IntermediateOperationFactory intermediateOperationFactory = RootFactory
            .getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);

        intermediateOperationOptimizers.add(new SquashSkip(intermediateOperationFactory));
        intermediateOperationOptimizers.add(new SquashLimit(intermediateOperationFactory));
        intermediateOperationOptimizers.add(new SquashFilter<>(intermediateOperationFactory));
        intermediateOperationOptimizers.add(new SquashDistinct(intermediateOperationFactory));
    }

    public Stream<IntermediateOperationOptimizer> stream() {
        return intermediateOperationOptimizers.stream();
    }

}
