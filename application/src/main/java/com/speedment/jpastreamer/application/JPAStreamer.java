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
package com.speedment.jpastreamer.application;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import javax.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.stream.Stream;

/**
 * A JPAStreamer is responsible for creating Streams from data sources
 * Entity sources can be RDBMSes, files or other data sources.
 *
 * A JPAStreamer must be thread safe and be able to handle several reading and
 * writing threads at the same time.
 *
 * @author Per Minborg
 * @since 0.1.0
 */
public interface JPAStreamer {

    /**
     * Creates and returns a new {@link Stream} over all entities in the
     * underlying data source (e.g database) according to the provided {@code streamConfiguration}.
     * This is the main query API for JPAstreamer.
     * <p>
     * The order in which elements are returned when the stream is eventually
     * consumed <em>is unspecified</em>. The order may even change from one
     * invocation to another. Thus, it is an error to assume any particular
     * element order even though is might appear, for some stream sources, that
     * there is a de-facto order.
     * <p>
     * If a deterministic order is required, then make sure to invoke the
     * {@link Stream#sorted(java.util.Comparator)} method on the {@link Stream}
     * returned.
     * <p>
     * Mutable elements are not reused within the stream. More formally, there
     * are no pair of mutable stream elements <code>e1</code> and
     * <code>e2</code> such that <code>e1 == e2</code>.
     * <p>
     * The Stream will never contain <code>null</code> elements.
     * <p>
     * This is <em>an inexpensive O(1) operation</em> that will complete in
     * constant time regardless of the number of entities in the underlying
     * database.
     * <p>
     * The returned stream is aware of its own pipeline and will optionally
     * <em>optimize its own pipeline</em> whenever it encounters a <em>Terminal
     * Operation</em> so that it will only iterate over a minimum set of
     * matching entities.
     * <p>
     * When a Terminal Operation is eventually called on the {@link Stream},
     * that execution time of the Terminal Operation will depend on the
     * optimized pipeline and the entities in the underlying database.
     * <p>
     * The Stream will be automatically
     * {@link Stream#onClose(java.lang.Runnable) closed} after the Terminal
     * Operation is completed or if an Exception is thrown during the Terminal
     * Operation.
     * <p>
     * Some of the <em>Terminal Operations</em> are:
     * <ul>
     * <li>{@link Stream#forEach(java.util.function.Consumer) forEach(Consumer)}
     * <li>{@link Stream#forEachOrdered(java.util.function.Consumer) forEachOrdered(Consumer)}
     * <li>{@link Stream#toArray() toArray()}
     * <li>{@link Stream#toArray(java.util.function.IntFunction) toArray(IntFunction)}
     * <li>{@link Stream#reduce(java.util.function.BinaryOperator) reduce(BinaryOperation}
     * <li>{@link Stream#reduce(java.lang.Object, java.util.function.BinaryOperator) reduce(Object, BinaryOperator)}
     * <li>{@link Stream#reduce(java.lang.Object, java.util.function.BiFunction, java.util.function.BinaryOperator) reduce(Object, BiFunction, BinaryOperator)}
     * <li>{@link Stream#collect(java.util.stream.Collector) collect(Collector)}
     * <li>{@link Stream#collect(java.util.function.Supplier, java.util.function.BiConsumer, java.util.function.BiConsumer) collect(Supplier, BiConsumer, BiConsumer)}
     * <li>{@link Stream#min(java.util.Comparator) min(Comparator)}
     * <li>{@link Stream#max(java.util.Comparator) min(Comparator)}
     * <li>{@link Stream#count() count()}
     * <li>{@link Stream#anyMatch(java.util.function.Predicate) anyMatch(Predicate)}
     * <li>{@link Stream#noneMatch(java.util.function.Predicate) noneMatch(Predicate)}
     * <li>{@link Stream#findFirst() findFirst()}
     * <li>{@link Stream#findAny() findAny()}
     * <li>{@link Stream#iterator() iterator()}
     * </ul>
     * <p>
     * Any Terminating Operation may throw an Exception if the
     * underlying database throws an Exception (e.g. an SqlException)
     * <p>
     * Because the Stream may short-circuit operations in the Stream pipeline,
     * methods having side-effects (like
     * {@link Stream#peek(java.util.function.Consumer) peek(Consumer)} will
     * potentially be affected by the optimization.
     * <p>
     * Here are some examples of how the stream optimization might work:
     * <ul>
     * <li>
     * <pre>{@code stream(Film.class)
     *   .filter(Film$.name.equal("Casablanca"))
     *   .collect(toList());}</pre>
     * <pre>{@code -> select * from film where name='Casablanca'}</pre>
     * </li>
     * <li>
     * <pre>{@code stream.count();}</pre>
     * <pre>{@code -> select count(*) from film}</pre>
     * </li>
     * <li>
     * <pre>{@code stream(Film.class)
     *   .filter(Film$.name.startsWith("A"))
     *   .count();}</pre>
     * <pre>{@code -> select count(*) from hares where
     *   name LIKE 'A%'}</pre>
     * <p>
     * </li>
     * <li>
     * <pre>{@code stream.stream(Film.class)
     *   .filter(Film$.rating.equal("G")
     *   .filter(Film$.length.greaterThan(100)
     *   .count();}</pre>
     * <pre>{@code -> select count(*) from hares where
     *          rating ='G'
     *        and
     *          length > 100}</pre>
     * </li>
     * </ul>
     *
     * @param <T> The element type (type of a class token)
     * @param streamConfiguration a configuration including an entity class (annotated with {@code @Entity})
     * @return a new stream over all entities in this table in unspecified order
     *
     * @throws RuntimeException if an error occurs during a Terminal Operation
     * (e.g. an SqlException is thrown by the underlying database)
     *
     * @see java.util.stream
     * @see Stream
     */
    <T> Stream<T> stream(StreamConfiguration<T> streamConfiguration);

    /**
     * Creates and returns a new {@link Stream} over all entities in the
     * underlying data source (e.g database) of the provided type
     * {@code entityClass}.
     * <p>
     * This method is a convenience method equivalent to:
     * <pre>{@code stream(StreamConfiguration.of(entityClass))}</pre>
     *
     * @param <T> The element type (type of a class token)
     * @param entityClass to use
     * @return a new {@link Stream} over all entities in the
     *         underlying data source (e.g database) of the provided type
     *         {@code entityClass}
     *
     * @see JPAStreamer#stream(StreamConfiguration) for furhter details
     */
    default <T> Stream<T> stream(final Class<T> entityClass) {
        requireNonNull(entityClass);
        return stream(StreamConfiguration.of(entityClass));
    }

    /**
     * Creates and returns a new {@link Stream} over all entities in the
     * underlying data source (e.g database) of the {@code entity} specified
     * by the provided {@code projection}.
     * <p>
     * This method is a convenience method equivalent to:
     * <pre>{@code stream(StreamConfiguration.of(projection.entityClass()).select(projection))}</pre>
     *
     * @param <T> The element type (type of a class token)
     * @param projection to use
     * @return a new {@link Stream} over all entities in the
     *         underlying data source (e.g database) of the {@code entity}
     *         specified by the provided {@code projection}.
     *
     * @see JPAStreamer#stream(StreamConfiguration) for further details
     */
    default <T> Stream<T> stream(final Projection<T> projection) {
        requireNonNull(projection);
        return stream(StreamConfiguration.of(projection.entityClass()).selecting(projection));
    }

    /**
     * Resets the Streamer associated with the provided Entity classes.
     * <p> 
     * This will create a new instance of the underlying {@code java.persistence.EntityManager}, removing all entries of the 
     * associated Entity class from the first-level cache. 
     * 
     * @param entityClasses of the streamer  
     */
    void resetStreamer(Class<?>... entityClasses); 
    
    /**
     * Closes this JPAStreamer and releases any resources potentially held.
     * <p>
     * If and only if this JPAStreamer was created using a {@code persistenceUnitName},
     * the underlying EntityManagerFactory will be closed.
     */
    void close();

    /**
     * Creates and returns a new JPAStreamerBuilder that will create a new
     * {@link EntityManagerFactory} using the provided {@code persistenceUnitName}.
     * <p>
     *  Call the JPAStreamerBuilder::build method to create a new JPAStreamer instance.
     * <p>
     * The newly created EntityManagerFactory will be closed whenever a built
     * JPAStreamer instance is closed.
     *
     * @param persistenceUnitName of the persistence unit as per the persistence.xml file
     * @return a new JPAStreamerBuilder
     */
    static JPAStreamerBuilder createJPAStreamerBuilder(final String persistenceUnitName) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(persistenceUnitName);
    }

    /**
     * Creates and returns a new JPAStreamerBuilder that will use the provided
     * {@code entityManagerFactory}.
     * <p>
     *  Call the JPAStreamerBuilder::build method to create a new JPAStreamer instance.
     * <p>
     * The provided {@code entityManagerFactory} will <em>not</em> be closed whenever a built
     * JPAStreamer instance is closed.
     *
     * @param entityManagerFactory to be used by the JPAStreamer
     * @return a new JPAStreamerBuilder
     */
    static JPAStreamerBuilder createJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        return RootFactory
            .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
            .create(entityManagerFactory);
    }

    /**
     * Creates and returns a new JPAStreamer that will create a new
     * {@link EntityManagerFactory} using the provided {@code persistenceUnitName}.
     * <p>
     * The newly created EntityManagerFactory will be closed whenever the returned
     * JPAStreamer instance is closed.
     *
     * @param persistenceUnitName of the persistence unit as per the persistence.xml file
     * @return a new JPAStreamer
     * @see JPAStreamer#createJPAStreamerBuilder(String)
     */
    static JPAStreamer of(final String persistenceUnitName) {
        requireNonNull(persistenceUnitName);
        return createJPAStreamerBuilder(persistenceUnitName).build();
    }

    /**
     * Creates and returns a new JPAStreamer that will use the provided
     * {@code entityManagerFactory}.
     * <p>
     * The provided {@code entityManagerFactory} will <em>not</em> be closed whenever
     * the returned JPAStreamer instance is closed.
     *
     * @param entityManagerFactory to be used by the JPAStreamer
     * @return a new JPAStreamerBuilder
     */
    static JPAStreamer of(final EntityManagerFactory entityManagerFactory) {
        requireNonNull(entityManagerFactory);
        return createJPAStreamerBuilder(entityManagerFactory).build();
    }

}
