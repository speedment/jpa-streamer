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

package com.speedment.jpastreamer.interopoptimizer.standard;

import com.speedment.jpastreamer.interopoptimizer.standard.internal.InternalIntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizer;

import java.util.stream.Stream;

public final class StandardIntermediateOperationOptimizerFactory implements IntermediateOperationOptimizerFactory {

    private final IntermediateOperationOptimizerFactory delegate = new InternalIntermediateOperationOptimizerFactory();

    public Stream<IntermediateOperationOptimizer> stream() {
        return delegate.stream();
    }


}
