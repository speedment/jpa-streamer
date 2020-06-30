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