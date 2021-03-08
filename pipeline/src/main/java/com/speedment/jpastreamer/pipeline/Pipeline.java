/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.LinkedList;
import java.util.List;

/**
 * Mutable Pipeline containing root (source),
 * intermediate and terminating operations of
 * a Stream.
 * <p>
 * This class is not thread-safe.
 *
 * @author  Per Minborg
 */
public interface Pipeline<T> {

    Class<T> root();

    /**
     * Returns a mutable LinkedList of the intermediate operations
     * in the pipeline.
     *
     * @return a mutable LinkedList of the intermediate operations
     *         in the pipeline
     */
    LinkedList<IntermediateOperation<?, ?>> intermediateOperations();

    TerminalOperation<?, ?> terminatingOperation();

    void terminatingOperation(TerminalOperation<?, ?> terminalOperation);

    /**
     * Returns whether this pipeline, if a terminal operation were to be
     * executed, would execute in parallel.
     *
     * @return {@code true} if this pipeline would execute in parallel if
     * executed
     */
    boolean isParallel();

    /**
     * Sets this Pipeline to parallel (not sequential).
     */
    void parallel();

    /**
     * Sets this Pipeline to sequential (not parallel).
     */
    void sequential();

    /**
     * Returns whether this pipeline, if a terminal operation were to be
     * executed, would execute unordered.
     *
     * @return {@code true} if this pipeline would execute unordered if
     * executed
     */
    boolean isUnordered();

    /**
     * Sets if this Pipeline is ordered.
     *
     * @param flag <code>true</code> if the Pipeline is ordered,
     * <code>false</code> if the Pipeline is unordered
     */
    void ordered(boolean flag);


    /**
     * Returns the close handlers for the pipeline.
     *
     * @return the close handler for the pipeline
     */
    List<Runnable> closeHandlers();

}
