package com.speedment.jpastreamer.field.trait;

/**
 * A representation of the first argument of a field predicate.
 *
 * @author Julia Gustafsson
 * @since  0.1.0
 */

public interface HasArg0<T0> {

    /**
     * Returns the first argument of a predicate.
     *
     * @return  the first argument
     */
    T0 get0();
}
