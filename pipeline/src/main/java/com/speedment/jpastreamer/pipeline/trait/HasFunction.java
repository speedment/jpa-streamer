package com.speedment.jpastreamer.pipeline.trait;

import java.util.function.Function;

public interface HasFunction<S, R> {

    /**
     * Returns a function, that when applied, will convert
     * from a source stream of type S to some result of type R.
     *
     * @return a function, that when applied, will convert
     *         from a source stream of type S to some result of type R
     *
     * @throws ClassCastException if the operation
     *         is not applicable (e.g. a terminating operation
     *         does not have a function but perhaps a consumer
     */
    Function<S, R> function();
}