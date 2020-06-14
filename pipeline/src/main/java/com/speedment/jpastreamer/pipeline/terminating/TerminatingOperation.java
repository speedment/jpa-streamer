package com.speedment.jpastreamer.pipeline.terminating;

import java.util.List;
import java.util.function.Function;
import java.util.stream.BaseStream;

public interface TerminatingOperation<S extends BaseStream<?, S>, R>  {

    TerminatingOperationType terminatingOperationType();

    Class<S> streamType();

    List<?> arguments();

    Class<R> returnType();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *       execute the TerminatingOperation
     */
    Function<S, R> function();

}