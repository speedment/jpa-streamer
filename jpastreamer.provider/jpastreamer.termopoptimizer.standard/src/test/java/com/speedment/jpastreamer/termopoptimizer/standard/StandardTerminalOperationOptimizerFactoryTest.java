package com.speedment.jpastreamer.termopoptimizer.standard;

import com.speedment.jpastreamer.termopoptimizer.TerminalOperationOptimizerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StandardTerminalOperationOptimizerFactoryTest {

    @Test
    void get() {
        final TerminalOperationOptimizerFactory terminalOperationOptimizerFactory = new StandardTerminalOperationOptimizerFactory();
        assertNotNull(terminalOperationOptimizerFactory.get());
    }
    
}
