package com.speedment.jpastreamer.application;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ServiceLoader;
import java.util.Set;

public interface StreamConfiguration<T> {

    Class<T> entityClass();

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