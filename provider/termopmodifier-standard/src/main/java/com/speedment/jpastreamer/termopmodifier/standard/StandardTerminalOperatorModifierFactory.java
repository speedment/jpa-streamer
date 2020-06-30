package com.speedment.jpastreamer.termopmodifier.standard;

import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier;
import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifierFactory;
import com.speedment.jpastreamer.termopmodifier.standard.internal.InternalTerminalOperatorModifierFactory;

public final class StandardTerminalOperatorModifierFactory implements TerminalOperationModifierFactory {

    private final TerminalOperationModifierFactory delegate = new InternalTerminalOperatorModifierFactory();

    @Override
    public TerminalOperationModifier get() {
        return delegate.get();
    }
}