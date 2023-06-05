package com.speedment.jpastreamer.termopmodifier.standard;

import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifierFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StandardTerminalOperationModifierFactoryTest {

    @Test
    void get() {
        final TerminalOperationModifierFactory terminalOperationModifierFactory = new StandardTerminalOperatorModifierFactory();
        assertNotNull(terminalOperationModifierFactory.get());
    }
    
}
