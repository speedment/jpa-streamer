package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ServiceLoader;
import java.util.Set;

public interface StreamConfiguration<T> {

    /**
     * Returns the entity class that is to appear in
     * a future Stream.
     *
     * @return the entity class that is to appear in
     *         a future Stream
     */
    Class<T> entityClass();

    /**
     * Returns the fields that shall be joined in
     * a future stream.
     * <p>
     * Joining fields <em></em>prevents N + 1 select problems</em>
     * in cases fields in the Set are to be used
     * by stream consumers.
     *
     * @return the fields that shall be joined in
     *         a future stream
     */
    Set<Field<T>> joins();

    /**
     * Creates and returns a new StreamConfigurationBuilder that can be used
     * to configure streams.
     * <p>
     *  Call the StreamConfigurationBuilder::build method to create a
     *  new StreamConfigurationBuilder instance.
     *
     * @param <T> The element type (type of a class token)
     * @param entityClass a class token for an entity class (annotated with {@code @Entity})
     * @return a new JPAStreamerBuilder
     */
    static <T> StreamConfigurationBuilder<T> builder(final Class<T> entityClass) {
        return RootFactory
                .getOrThrow(StreamConfigurationBuilderFactory.class, ServiceLoader::load)
                .createStreamConfigurationBuilder(entityClass);

    }

}