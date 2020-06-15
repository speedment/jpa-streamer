package com.speedment.jpastreamer.pipeline.terminating;

import java.util.function.*;
import java.util.stream.*;

public interface TerminatingOperation<S extends BaseStream<?, S>, R>  {

    /**
     * Returns the terminating operation type.
     *
     * @return the terminating operation type
     * @see TerminatingOperationType
     */
    TerminatingOperationType terminatingOperationType();

    /**
     * Returns the stream type on which the terminating operation
     * is supposed to be invoked.
     * <p>
     * Any of the following classes can be returned:
     * <ul>
     *     <li>{@link Stream}</li>
     *     <li>{@link IntStream}</li>
     *     <li>{@link LongStream}</li>
     *     <li>{@link DoubleStream}</li>
     * </ul>
     *
     * @return the stream type on which the terminating operation
     *         is supposed to be invoked
     */
    Class<? super S> streamType();

    /**
     * Returns the arguments for this terminating operation, or {@code null}.
     *
     * @return the arguments for this terminating operation, or {@code null}
     */
    Object[] arguments();

    /**
     * Returns the return type of the terminating operation.
     * <p>
     * Potential return types include:
     * <ul>
     *     <li>{@code long.class}, e.g. for {@code count()}</li>
     *     <li>{@code Object.class}, e.g. for {@code collect()}</li>
     *     <li>{@code void.class}, e.g. for {@code forEach()}</li>
     *     <li>{@code boolean.class}, e.g. for {@code anyMatch()}</li>
     *     <li>{@code Object[].class}, e.g. for {@code toArray()}</li>
     *     <li>{@code Optional.class}, e.g. for {@code findAny()}</li>
     * </ul>
     *
     * @return the return type of the terminating operation
     */
    Class<? super R> returnType();

    /**
     * Returns a function to be applied in order to
     * execute the TerminatingOperation on a target
     * Stream of type S.
     *
     * @return the function to be applied in order to
     *         execute the TerminatingOperation
     *
     * @throws ClassCastException if the terminating operation
     *         does not return an object or array.
     */
    Function<S, R> function();

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