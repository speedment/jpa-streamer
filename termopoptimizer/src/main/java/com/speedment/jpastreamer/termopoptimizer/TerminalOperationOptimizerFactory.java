package com.speedment.jpastreamer.termopoptimizer;

@FunctionalInterface
public interface TerminalOperationOptimizerFactory {

    /**
     * Returns a TerminalOperationOptimizer.
     *
     * @return a TerminalOperationOptimizer
     */
    TerminalOperationOptimizer get();

}