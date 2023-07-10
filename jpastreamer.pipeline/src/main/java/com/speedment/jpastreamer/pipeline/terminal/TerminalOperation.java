/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.pipeline.terminal;

import com.speedment.jpastreamer.pipeline.trait.*;

import java.util.function.*;
import java.util.stream.*;

public interface TerminalOperation<S extends BaseStream<?, S>, R> extends
        HasType<TerminalOperationType>,
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
