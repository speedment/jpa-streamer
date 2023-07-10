/**
 * Provides interfaces for configuring custom Stream sources. 
 *
 * {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} instances are used to specify certain properties of a {@link java.util.stream.Stream}.
 * Instances of {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} are guaranteed to be immutable and therefore inherently thread-safe.
 * 
 * <h2>API Usage</h2>
 *
 * The following example demonstrates the usage of {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} to configure a {@link java.util.stream.Stream}
 * for querying films, joining with related entities, and applying filtering operations:
 *
 * <pre>{@code
 * JPAStreamer jpaStreamer = JPAStreamer.of("sakila"); 
 * 
 * jpaStreamer.stream(
 *     StreamConfiguration.of(Film.class)
 *         .joining(Film$.actors))
 * )
 *      .filter(Film$.length.between(100, 120))
 *      .forEach(System.out::println);
 * }</pre>
 *
 * <p>
 * In this example a {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} is created using the static {@code of} method, specifying the entity class (`Film` in this case).
 * The {@code joining}-method is then used to define the entities to join with (in this case, `actors` and `language`).
 * The resulting {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} instance is then used to create a stream using the {@code JPAStreamer.stream()} method.
 * A filtering operation is applied on the stream to include films with a length between 100 and 120 and the results are printed. 
 * </p>
 *
 * This following example demonstrates the usage of {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} to configure a stream
 * for querying films and applying sorting and limiting operations:
 *
 * <pre>{@code
 *
 * JPAStreamer jpaStreamer = JPAStreamer.of("sakila");
 *
 * jpaStreamer.stream(
 *          StreamConfiguration.of(Film.class)
 *              .selecting(Projection.select(Film$.filmId, Film$.title)
 * )
 *     .sorted(Film$.length.reversed())
 *     .limit(3)
 *     .forEach(System.out::println);
 * }</pre>
 *
 * <p>
 * In the example above, a {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} is created using the static {@code of} method, specifying the entity class ({@code Film} in this case).
 * The {@code selecting} method is then used to define the projections (selected properties) for the query.
 * The resulting {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} instance is then used to create a stream using the {@code JPAStreamer.stream()} method.
 * Sorting and limiting operations are applied on the stream before printing the results.
 * </p>
 *
 * <p>
 * Note that {@link com.speedment.jpastreamer.streamconfiguration.StreamConfiguration} instances are immutable, meaning that any modification operations will return a new instance,
 * ensuring thread-safety and preventing unintended side effects.
 * </p>
 */
package com.speedment.jpastreamer.streamconfiguration;

