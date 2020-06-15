package com.speedment.jpastreamer.pipeline.trait;

public interface HasArguments {

    /**
     * Returns the arguments for this terminating operation, or {@code null}.
     *
     * @return the arguments for this terminating operation, or {@code null}
     */
    Object[] arguments();
}
