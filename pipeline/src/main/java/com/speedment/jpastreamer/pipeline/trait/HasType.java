package com.speedment.jpastreamer.pipeline.trait;

import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperationType;

public interface HasType<E extends Enum<E>> {

    /**
     * Returns the type of the operation.
     *
     * @return the type of the operation
     * @see TerminatingOperationType
     */
    E type();

}
