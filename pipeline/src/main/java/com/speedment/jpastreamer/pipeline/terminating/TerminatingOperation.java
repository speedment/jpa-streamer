package com.speedment.jpastreamer.pipeline.terminating;

import com.speedment.jpastreamer.pipeline.trait.*;

import java.util.function.*;
import java.util.stream.*;

public interface TerminatingOperation<S extends BaseStream<?, S>, R> extends
        HasType<TerminatingOperationType>,
        HasStreamType<S>,
        HasReturnType<R>,
        HasArguments,
        HasFunction<S, R> {

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return a long
     */
    ToLongFunction<S> toLongFunction();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return an int
     */
    ToIntFunction<S> toIntFunction();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return a double
     */
    ToDoubleFunction<S> toDoubleFunction();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return a boolean
     */
    Predicate<S> predicate();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return void
     */
    Consumer<S> consumer();

    //todo add others...

}