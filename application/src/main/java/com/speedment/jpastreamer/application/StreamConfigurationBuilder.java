package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.field.Field;

public interface StreamConfigurationBuilder<T> {

    /**
     * Configures the the provided {@code field} so that it will be
     * eagerly joined when producing the elements in the future Stream.
     * <p>
     * This prevents the N+1 problem if the field is accessed in
     * elements in the future Stream.
     * </p>
     * @param field to join
     * @return this StreamConfigurationBuilder
     */
    StreamConfigurationBuilder<T> joining(Field<T> field);

    /**
     * Creates an returns a new immutable StreamConfiguration
     * instance.
     * @return a new immutable StreamConfiguration
     *         instance
     */
    StreamConfiguration<T> build();

}