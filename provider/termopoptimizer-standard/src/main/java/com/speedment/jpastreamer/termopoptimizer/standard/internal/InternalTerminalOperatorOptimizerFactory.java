package com.speedment.jpastreamer.termopoptimizer.standard.internal;

import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizer;
import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizerFactory;

public final class InternalTerminalOperatorOptimizerFactory implements TerminalOperationOptimizerFactory{

    private final TerminalOperationOptimizer singleton = new StandardTerminalOperatorOptimizer();

    @Override
    public TerminalOperationOptimizer get() {
        return singleton;
    }
}