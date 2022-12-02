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
package com.speedment.jpastreamer.field.collector;

import com.speedment.jpastreamer.field.*;
import com.speedment.jpastreamer.field.internal.collector.FieldCollectorImpl;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;

/**
 * A number of collectors specialized for entities. These are inspired by the
 * {@link java.util.stream.Collectors} class in the java standard libraries.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class FieldCollectors {
    private FieldCollectors() {}

    public static <ENTITY> Collector<ENTITY, ?, Map<Long, List<ENTITY>>>
    groupingBy(LongField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Integer, List<ENTITY>>>
    groupingBy(IntField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Short, List<ENTITY>>>
    groupingBy(ShortField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Byte, List<ENTITY>>>
    groupingBy(ByteField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Double, List<ENTITY>>>
    groupingBy(DoubleField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Float, List<ENTITY>>>
    groupingBy(FloatField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<Boolean, List<ENTITY>>>
    groupingBy(BooleanField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }

    public static <ENTITY> Collector<ENTITY, ?, Map<Character, List<ENTITY>>>
    groupingBy(CharField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY> Collector<ENTITY, ?, Map<String, List<ENTITY>>>
    groupingBy(StringField<ENTITY> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, T> Collector<ENTITY, ?, Map<T, List<ENTITY>>>
    groupingBy(ReferenceField<ENTITY, T> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Long, R>>
    groupingBy(LongField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Integer, R>>
    groupingBy(IntField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Short, R>>
    groupingBy(ShortField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Byte, R>>
    groupingBy(ByteField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Double, R>>
    groupingBy(DoubleField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Float, R>>
    groupingBy(FloatField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Boolean, R>>
    groupingBy(BooleanField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<Character, R>>
    groupingBy(CharField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, A, R> Collector<ENTITY, ?, Map<String, R>>
    groupingBy(StringField<ENTITY> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, T, A, R> Collector<ENTITY, ?, Map<T, R>>
    groupingBy(ReferenceField<ENTITY, T> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <T, K, D, A, M extends Map<K, D>>
    FieldCollector<T, ?, M> groupingBy(
            Field<T> field,
            Function<T, K> classifier,
            Supplier<M> mapFactory,
            Collector<? super T, A, D> downstream) {

        Supplier<A> downstreamSupplier = downstream.supplier();
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            A container = m.computeIfAbsent(key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };
        BinaryOperator<Map<K, A>> merger = FieldCollectors.mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;

        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new FieldCollectorImpl<>(field, mangledFactory, accumulator, merger, CH_ID);
        }
        else {
            @SuppressWarnings("unchecked")
            UnaryOperator<A> downstreamFinisher = (UnaryOperator<A>) downstream.finisher();
            Function<Map<K, A>, M> finisher = intermediate -> {
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new FieldCollectorImpl<>(field, mangledFactory, accumulator, merger, finisher, CH_NOID);
        }
    }

    private static <K, V, M extends Map<K,V>>
    BinaryOperator<M> mapMerger(BinaryOperator<V> mergeFunction) {
        return (m1, m2) -> {
            for (Map.Entry<K,V> e : m2.entrySet()) {
                m1.merge(e.getKey(), e.getValue(), mergeFunction);
            }
            return m1;
        };
    }

    private static final Set<Collector.Characteristics> CH_ID =
        unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    
    private static final Set<Collector.Characteristics> CH_NOID = emptySet();
}
