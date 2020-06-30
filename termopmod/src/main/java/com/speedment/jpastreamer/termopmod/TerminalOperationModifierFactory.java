package com.speedment.jpastreamer.termopmod;

@FunctionalInterface
public interface TerminalOperationModifierFactory {

    /**
     * Returns a TerminalOperationModifier.
     *
     * @return a TerminalOperationModifier
     */
    TerminalOperationModifier get();

}