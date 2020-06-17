package com.speedment.jpastreamer.pipeline.standard.internal.terminal;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;

public class Main {

    public static void main(String[] args) {
        TerminalOperationFactory factory = new InternalTerminalOperationFactory();

        factory.acquireCount();

    }

}
