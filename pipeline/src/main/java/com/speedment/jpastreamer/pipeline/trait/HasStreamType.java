package com.speedment.jpastreamer.pipeline.trait;

import java.util.stream.*;

public interface HasStreamType<S extends BaseStream<?, S>> {

    /**
     * Returns the stream type on which the operation
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
     * @return the stream type on which the operation
     *         is supposed to be invoked
     */
    Class<? super S> streamType();

}
