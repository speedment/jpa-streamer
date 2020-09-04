/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.streamconfiguration;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ServiceLoader;
import java.util.Set;

/**
 * StreamConfiguration instances are used
 * to specify certain properties of a Stream.
 * <p>
 * Instances are guaranteed to be immutable and
 * therefore inherently thread-safe.
 *
 * @param <T> the entity type
 */
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
     * Creates and returns a new StreamConfiguration configured with
     * the provided {@code field} so that it will be
     * eagerly joined when producing elements in the future Stream.
     * <p>
     * This prevents the N+1 problem if the field is accessed in
     * elements in the future Stream.
     * </p>
     * @param field to join
     * @return this StreamConfigurationBuilder
     */
    StreamConfiguration<T> joining(Field<T> field);

    // BEGIN: Mandates implementation
    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

    @Override
    String toString();
    // END: Mandates implementation

    /**
     * Creates and returns a new StreamConfiguration that can be used
     * to configure streams.
     * <p>
     * The method guarantees that the instances (and subsequent derived
     * instances) are immutable. The method further guarantees that
     * any object obtained from instances (or subsequent derived instances)
     * are immutable or unmodifiable.
     *
     * @param <T> The element type (type of a class token)
     * @param entityClass a class token for an entity class (annotated with {@code @Entity})
     * @return a new JPAStreamerBuilder
     */
    static <T> StreamConfiguration<T> of(final Class<T> entityClass) {
        return RootFactory
                .getOrThrow(StreamConfigurationFactory.class, ServiceLoader::load)
                .createStreamConfiguration(entityClass);

    }

}