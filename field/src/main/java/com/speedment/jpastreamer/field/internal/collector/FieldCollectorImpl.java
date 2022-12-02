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
package com.speedment.jpastreamer.field.internal.collector;

import com.speedment.jpastreamer.field.collector.FieldCollector;
import com.speedment.jpastreamer.field.Field;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the entity type to be collected
 * @param <A>  the intermediate accumulation type of the downstream collector
 * @param <R>  the collected result
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class FieldCollectorImpl<T, A, R>
implements FieldCollector<T, A, R> {

    private final Field<T> field;
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Collector.Characteristics> characteristics;

    public FieldCollectorImpl(
                Field<T> field,
                Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher,
                Set<Collector.Characteristics> characteristics) {
        
        this.field           = requireNonNull(field);
        this.supplier        = requireNonNull(supplier);
        this.accumulator     = requireNonNull(accumulator);
        this.combiner        = requireNonNull(combiner);
        this.finisher        = requireNonNull(finisher);
        this.characteristics = requireNonNull(characteristics);
    }

    public FieldCollectorImpl(
            Field<T> field,
            Supplier<A> supplier,
            BiConsumer<A, T> accumulator,
            BinaryOperator<A> combiner,
            Set<Collector.Characteristics> characteristics) {
        
        this(field, supplier, accumulator, combiner, castingIdentity(), characteristics);
    }
    
    @Override
    public Field<T> getField() {
        return field;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return accumulator;
    }

    @Override
    public Supplier<A> supplier() {
        return supplier;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return combiner;
    }

    @Override
    public Function<A, R> finisher() {
        return finisher;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return characteristics;
    }
    
    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }
}
