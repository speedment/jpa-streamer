package com.speedment.jpastreamer.pipeline.trait;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

public interface HasType<E extends Enum<E>> {

    /**
     * Returns the type of the operation.
     *
     * @return the type of the operation
     * @see TerminalOperationType
     */
    E type();

}
