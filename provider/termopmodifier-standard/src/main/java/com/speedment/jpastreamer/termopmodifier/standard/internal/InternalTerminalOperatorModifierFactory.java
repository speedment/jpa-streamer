package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier;
import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifierFactory;

public final class InternalTerminalOperatorModifierFactory implements TerminalOperationModifierFactory {

    private final TerminalOperationModifier singleton = new StandardTerminalOperatorModifier();

    @Override
    public TerminalOperationModifier get() {
        return singleton;
    }
}