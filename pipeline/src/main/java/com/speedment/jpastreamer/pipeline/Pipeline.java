/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.action.Action;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

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
    LinkedList<Action<?, ?>> intermediateOperations();

    TerminatingOperation<?, ?> terminatingOperation();

    void terminatingOperation(TerminatingOperation<?, ?> terminatingOperation);

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