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
package com.speedment.jpastreamer.termopoptimizer.standard;

import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizer;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizerFactory;
import com.speedment.jpastreamer.termopoptimizer.standard.internal.InternalTerminalOperatorOptimizerFactory;

public final class StandardTerminalOperatorModifierFactory implements TerminalOperationOptimizerFactory {

    private final TerminalOperationOptimizerFactory delegate = new InternalTerminalOperatorOptimizerFactory();

    @Override
    public TerminalOperationOptimizer get() {
        return delegate.get();
    }
}
