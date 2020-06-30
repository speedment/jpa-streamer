package com.speedment.jpastreamer.termopmodifier;

@FunctionalInterface
public interface TerminalOperationModifierFactory {

    /**
     * Returns a TerminalOperationModifier.
     *
     * @return a TerminalOperationModifier
     */
    TerminalOperationModifier get();

}