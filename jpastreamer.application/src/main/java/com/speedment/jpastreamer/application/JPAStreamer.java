/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A JPAStreamer is responsible for creating Streams from data sources,
 * alternatively for creating {@link StreamSupplier}s that can be reused to create Streams of the same Entity source, 
 * see {@link JPAStreamer#createStreamSupplier(StreamConfiguration)}. Entity sources can be RDBMSes, files or other data sources. 
 * <p>
 * A JPAStreamer must be thread safe and able to handle several reading and
 * writing threads at the same time.
 * <p>
 * JPAStreamer can be instantiated using different approaches depending on your requirements.
 *
 * <h3>1. Using the persistence unit name</h3>
 * The persistence unit name can be used to initialize JPAStreamer as follows:
 * <pre>{@code
 * final JPAStreamer jpaStreamer = JPAStreamer.of("sakila");
 * }</pre>
 *
 * Replace "sakila" with the name of your persistence unit as specified in the JPA configuration file.
 * JPAStreamer will establish a database connection based on the provided persistence unit name.
 * This method creates a new {@code jakarta.persistence.EntityManagerFactory} managed by JPAStreamer. 
 *
 * <h3>2. Using an existing EntityManagerFactory</h3>
 * If you already have an existing {@code EntityManagerFactory}, you can initialize JPAStreamer as follows:
 * <pre>{@code
 * final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sakila");
 * final JPAStreamer jpaStreamer = JPAStreamer.of(emf);
 * }</pre>
 *
 * In this case, JPAStreamer will not manage the lifecycle of the {@code EntityManagerFactory}. 
 * The provided {@code EntityManagerFactory} will be used to obtain {@code EntityManager}s, which will be managed by JPAStreamer.
 *
 * <h3>3. Using a Supplier of EntityManagers</h3>
 * Another third option is to provide a Supplier of EntityManagers:
 * <pre>{@code
 * final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sakila");
 * final JPAStreamer jpaStreamer = JPAStreamer.of(emf::createEntityManager);
 * }</pre>
 * This approach is useful when you want to supply your own EntityManagers.
 * JPAStreamer will not manage the lifecycle of the EntityManagers supplied through the Supplier.
 * @author Per Minborg
 * @since 0.1.0
 */
public interface JPAStreamer {

    /**
     * Creates and returns a new {@link Stream} over all entities in the
     * underlying data source (e.g database) according to the provided {@code streamConfiguration}.
     * <p>
     * The order in which elements are returned when the stream is eventually
     * consumed <em>is unspecified</em>. The order may even change from one
     * invocation to another. Thus, it is an error to assume any particular
     * element order even though it might appear that there is a de-facto 
     * order for some stream sources. If a deterministic order is required, 
     * then make sure to invoke the {@link Stream#sorted(java.util.Comparator)}
     * method on the {@link Stream} returned.
     * <p>
     * Mutable elements are not reused within the stream. More formally, there
     * are no pair of mutable stream elements <code>e1</code> and
     * <code>e2</code> such that <code>e1 == e2</code>. The {@link Stream} will never 
     * contain <code>null</code> elements.
     * <p>
     * This is <em>an inexpensive O(1) operation</em> that will complete in
     * constant time regardless of the number of entities in the underlying
     * database.
     * <p>
     * The returned {@link Stream} is aware of its own pipeline and will optionally
     * <em>optimize its own pipeline</em> whenever it encounters a <em>Terminal
     * Operation</em> so that it will only iterate over a minimum set of
     * matching entities. Thus, the execution time of the Terminal Operation 
     * will depend on the optimized pipeline and the entities in the underlying database.
     * Because the Stream may short-circuit operations in the Stream pipeline,
     * methods having side-effects (like {@link Stream#peek(java.util.function.Consumer) 
     * peek(Consumer)} will potentially be affected by the optimization.
     * <p>
     * The Stream will be automatically
     * {@link Stream#onClose(java.lang.Runnable) closed} after the Terminal
     * Operation is completed or if an Exception is thrown during the Terminal
     * Operation. Any Terminating Operation may throw an Exception if the
     * underlying database throws an Exception (e.g. an SqlException)
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
     * Here are some examples of how the {@link Stream} optimization might work:
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
     * <pre>{@code -> select count(*) from Film where
     *   name LIKE 'A%'}</pre>
     * <p>
     * </li>
     * <li>
     * <pre>{@code stream.stream(Film.class)
     *   .filter(Film$.rating.equal("G")
     *   .filter(Film$.length.greaterThan(100)
     *   .count();}</pre>
     * <pre>{@code -> select count(*) from Film where
     *          rating ='G'
     *        and
     *          length > 100}</pre>
     * </li>
     * </ul>
     *
     * @param <T> the type of the stream elements
     * @param streamConfiguration a configuration including an entity class (annotated with {@code @Entity})
     * @return a new {@link Stream} over all entities in the
                underlying data source (e.g database) described by the provided 
                {@code streamConfiguration}
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
     * @param <T> the type of the stream elements
     * @param entityClass to use
     * @return a new {@link Stream} over all entities in the
     *         underlying data source (e.g database) of the provided type
     *         {@code entityClass}
     *
     * @see JPAStreamer#stream(StreamConfiguration) for further details
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
     *     
     * This method is a convenience method equivalent to:
     * <pre>{@code stream(StreamConfiguration.of(projection.entityClass()).select(projection))}</pre>
     *
     * @param <T> the type of the stream elements
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
     * Creates and returns a new {@link StreamSupplier} that can create 
     * {@link Stream}s over all entities in the underlying data source (e.g database) 
     * of the provided type {@code entityClass}. 
     * <p>
     * The provided {@link StreamSupplier} will <em>not</em> be closed whenever
     * the generated {@link Stream} instance is closed.
     * <p>
     * If you are using the same Stream source frequently e.g. Film.class, 
     * consider configuring a {@link StreamSupplier} that can supply 
     * {@link Stream}s from the same source over and over again. 
     * This save resources and avoids instantiating a new {@link EntityManager} for each new {@link Stream}.
     * <p>
     * Here is an example of using a {@link StreamSupplier}: 
     * <pre>{@code
     *    final StreamSupplier<Film> streamSupplier = jpaStreamer.createStreamSupplier(Film.class);
     *    List<Film> longFilms = streamSupplier.stream()
     *       .filter(Film$.name.equal("Casablanca"))
     *       .collect(toList()); // the terminal operation does not close the Stream Supplier and its Entity Manager
     *     
     *    // ... repeated uses of the supplier 
     *      
     *    streamSupplier.close(); // closes the Entity Manager
     * }</pre>
     * The above is equal to:  
     * <pre>{@code
     *    List<Film> films = jpaStreamer.stream(Film.class) 
     *       .filter(Film$.name.equal("Casablanca"))
     *       .collect(toList()); // the terminal operation closes the underlying StreamSupplier and its Entity Manager
     * }</pre>
     * <p>
     *
     * @param <T> the type of the stream elements
     * @param streamConfiguration a configuration including an entity class (annotated with {@code @Entity})
     * @return a new {@link StreamSupplier} that can create 
     *          {@link Stream}s over all entities in the
     *          underlying data source (e.g database) described by the provided 
     *          {@code streamConfiguration}
     */
    <T> StreamSupplier<T> createStreamSupplier(StreamConfiguration<T> streamConfiguration);

    /**
     * Creates and returns a new {@link StreamSupplier} that can create 
     * {@link Stream}s over all entities in the underlying data source (e.g database) 
     *  of the provided type {@code entityClass}.
     * <p>
     * This method is a convenience method equivalent to:
     * <pre>{@code createStreamer(StreamConfiguration.of(entityClass))}</pre>
     *
     * @param <T> the type of the stream elements
     * @param entityClass to use in generated {@link Stream}s
     * @return a new {@link StreamSupplier} that can create 
     *          {@link Stream}s over all entities in the
     *          underlying data source (e.g database) of the provided 
     *          type {@code entityClass}
     *
     * @see JPAStreamer#createStreamSupplier(StreamConfiguration) (StreamConfiguration) for further details
     */
    default <T> StreamSupplier<T> createStreamSupplier(final Class<T> entityClass) {
        requireNonNull(entityClass); 
        return createStreamSupplier(StreamConfiguration.of(entityClass)); 
    }

    /**
     * Creates and returns a new {@link StreamSupplier} that can create 
     * {@link Stream}s over all entities in the underlying data source (e.g database) 
     *  of the {@code entity} specified by the provided {@code projection}.
     * <p>
     * This method is a convenience method equivalent to:
     * <pre>{@code createStreamer(StreamConfiguration.of(entityClass))}</pre>
     *
     * @param <T> the type of the stream elements
     * @param projection to use 
     * @return a new {@link StreamSupplier} that can create 
     *          {@link Stream}s over all entities in the
     *          underlying data source (e.g database) of the {@code entity}
     *          specified by the provided {@code projection}.
     *
     * @see JPAStreamer#createStreamSupplier(StreamConfiguration) (StreamConfiguration) for further details
     */
    default <T> StreamSupplier<T> createStreamSupplier(final Projection<T> projection) {
        requireNonNull(projection); 
        return createStreamSupplier(StreamConfiguration.of(projection.entityClass()).selecting(projection));
    }
    
    /**
     * Resets the Streamer associated with the provided Entity classes.
     * <p> 
     * This will create a new instance of the underlying {@code jakarta.persistence.EntityManager}, removing all entries of the 
     * associated Entity class from the first-level cache. The old {@code jakarta.persistence.EntityManager} is closed upon removal from the cache. 
     * 
     * In case JPAStreamer was configured with a {@code Supplier<EntityManager>} the lifecycle of the Entity Managers is 
     * not managed by JPAStreamer, thus use of the method is not permitted and will result in an {@code UnsupportedOperationException}. 
     * 
     * @deprecated since 3.0.2, JPAStreamer no longer caches Streamers thus there is no need for a method that resets the cache.
     * If you wish to manage the Streamer lifecycle manually, see {@link JPAStreamer#createStreamSupplier(StreamConfiguration)}
     * @param entityClasses of the streamer  
     * @throws UnsupportedOperationException if JPAStreamer is configured with a Supplier, see {@code com.speedment.jpastreamer.application.JPAStreamer#of(java.util.function.Supplier)}
     */
    @Deprecated(since = "3.0.2", forRemoval = true)
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
     *
     * Creates and returns a new JPAStreamerBuilder that will use the provided
     * {@code entityManagerSupplier}.
     * <p>
     * Call the JPAStreamerBuilder::build method to create a new JPAStreamer instance.
     * <p>
     * EntityManagers provided by the {@code entityManagerSupplier} will <em>not</em> 
     * be closed whenever a built JPAStreamer instance is closed.
     * <p>
     * This is a preview and may be subject to change. 
     * 
     * @param entityManagerSupplier to be used by the JPAStreamer
     * @since 1.1.1 
     * @return a new JPAStreamerBuilder
     */
    static JPAStreamerBuilder createJPAStreamerBuilder(final Supplier<EntityManager> entityManagerSupplier) {
        return RootFactory
                .getOrThrow(JPAStreamerBuilderFactory.class, ServiceLoader::load)
                .create(entityManagerSupplier);
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

    /**
     * Creates and returns a new JPAStreamer that will use the provided
     * {@code entityManagerSupplier}. 
     * <p>
     * EntityManagers provided by the {@code entityManagerSupplier} will 
     * <em>not</em> be closed whenever the returned JPAStreamer instance is closed.
     *
     * @param entityManagerSupplier to be used by the JPAStreamer
     * @return a new JPAStreamerBuilder
     */
    static JPAStreamer of(final Supplier<EntityManager> entityManagerSupplier) {
        requireNonNull(entityManagerSupplier);
        return createJPAStreamerBuilder(entityManagerSupplier).build();
    }
    
}
