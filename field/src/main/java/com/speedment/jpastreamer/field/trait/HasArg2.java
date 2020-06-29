package com.speedment.jpastreamer.field.trait;

/**
 * A representation of the third argument of a field predicate.
 *
 * @author Julia Gustafsson
 * @since  0.1.0
 */

public interface HasArg2<T2> {

    /**
     * Returns the third argument of a predicate.
     *
     * @return  the third argument
     */
    T2 get2();
}