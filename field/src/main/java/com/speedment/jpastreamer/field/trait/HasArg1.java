package com.speedment.jpastreamer.field.trait;

/**
 * A representation of the second argument of a field predicate.
 *
 * @author Julia Gustafsson
 * @since  0.1.0
 */

public interface HasArg1<T1> {

    /**
     * Returns the second argument of a predicate.
     *
     * @return  the second argument
     */
    T1 get1();
}
