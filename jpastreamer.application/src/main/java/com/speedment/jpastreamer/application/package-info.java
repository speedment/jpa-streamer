/**
 * Provides interfaces for instantiating JPAStreamer and building Stream queries.
 * 
 * JPAStreamer uses a custom implementation of the standard {@code java.util.Stream} interface
 * to build Stream queries that are automatically optimized to database queries. Here is a typical example: 
 *
 * <pre>{@code}
 * final JPAStreamer jpaStreamer = JPAStreamer.of("sakila"); // sakila is the name of the persistence unit 
 * 
 * final List&gt;Film&lt; longFilms = jpaStreamer.stream(Film.class)    // FROM 
 *      .filter(Film$.length.greaterThan(120))                          // WHERE 
 *      .sorted(Film$.length)                                           // ORDER BY
 *      .limit(20)                                                      // LIMIT
 *      .collect(Collectors.toList()); 
 * }</pre>
 * 
 *  In the example above, a {@code Stream} is created over entities in
 *  the entity class <em>Film</em> (<em>Film</em> must be described as a standard JPA entity in the application). 
 *  Filter, sorting and limiting operations are applied on the {@code Stream} before the results are collected as a {@code List}. 
 *  The terminal operation {@code collect()} triggers the translation of the Stream pipeline to an optimized query. 
 *  In this case, the pipeline is translated to the following query: 
 *
 * <pre>{@code}
 * SELECT (*) 
 * FROM Film 
 * WHERE length &lt; 120
 * ORDER BY length   
 * LIMIT 20 
 * </pre>
 *  
 * The optimizations are made to ensure that only the resulting entities are materialised in the {@code Stream}. 
 */
package com.speedment.jpastreamer.application;
