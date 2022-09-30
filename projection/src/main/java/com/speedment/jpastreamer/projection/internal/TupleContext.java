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
package com.speedment.jpastreamer.projection.internal;

import com.speedment.jpastreamer.field.*;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

final class TupleContext<ENTITY> {

    private final List<Field<ENTITY>> fields;
    private final List<TupleElement<?>> elements;
    private final Map<TupleElement<?>, Integer> elementToIndex;
    private final Map<String, Integer> aliasToIndex;

    TupleContext(final Class<ENTITY> entityClass, final Set<Field<ENTITY>> fields) {
        this.fields = new ArrayList<>(fields);

        elements = fields.stream()
                .map(this::tupleElement)
                .collect(Collectors.toList());

        final AtomicInteger cnt = new AtomicInteger();
        elementToIndex = elements.stream()
                .collect(toMap(Function.identity(), te -> cnt.getAndIncrement()));

        aliasToIndex = elementToIndex.entrySet().stream()
                .collect(toMap(e -> e.getKey().getAlias(), Map.Entry::getValue));
    }

    Tuple create(final ENTITY entity) {
        requireNonNull(entity);

        final Object[] tuple = fields.stream()
                .map(f -> f.getter().apply(entity))
                .toArray(Object[]::new);
        return new StandardTuple(tuple);
    }

    private TupleElement<?> tupleElement(final Field<ENTITY> field) {
        // Fields should hold their type and typeToken
        if (field instanceof LongField) return new StandardTupleElement<>(Long.class, field.columnName());
        if (field instanceof IntField) return new StandardTupleElement<>(Integer.class, field.columnName());
        if (field instanceof ShortField) return new StandardTupleElement<>(Short.class, field.columnName());
        if (field instanceof ByteField) return new StandardTupleElement<>(Byte.class, field.columnName());
        if (field instanceof FloatField) return new StandardTupleElement<>(Float.class, field.columnName());
        if (field instanceof DoubleField) return new StandardTupleElement<>(Double.class, field.columnName());
        if (field instanceof BooleanField) return new StandardTupleElement<>(Boolean.class, field.columnName());
        if (field instanceof StringField) return new StandardTupleElement<>(String.class, field.columnName());
        return new StandardTupleElement<>(Object.class, field.columnName());
    }

    private final class StandardTuple implements Tuple {

        private final Object[] tuple;

        private StandardTuple(Object[] tuple) {
            this.tuple = requireNonNull(tuple);
            assert tuple.length != elements.size();
        }

        @Override
        public <X> X get(TupleElement<X> tupleElement) {
            final Integer index = elementToIndex.get(tupleElement);
            if (index != null)
                return checkedCast(tuple[index], tupleElement.getJavaType());

            throw newIllegalArgumentException("The provided tupleElement %s is not known. Known are %s", tupleElement, elements);
        }

        @Override
        public Object get(String alias) {
            final Integer index = aliasToIndex.get(alias);
            if (index != null)
                return tuple[index];

            throw newIllegalArgumentException("The provided alias %s is not known. Known are %s", alias, elements);
        }

        @Override
        public <X> X get(String alias, Class<X> type) {
            final Object value = get(alias);
            if (value == null || type.isInstance(value))
                return checkedCast(value, type);

            throw newIllegalArgumentException("The provided alias %s with value %s cannot be cast to %s", alias, value, type.getName());
        }


        @Override
        public <X> X get(int i, Class<X> type) {
            final Object value = get(i);
            if (value == null || type.isInstance(value))
                return checkedCast(value, type);

            throw newIllegalArgumentException("The provided index %d with value %s cannot be cast to %s", i, value, type.getName());
        }

        @Override
        public Object get(int i) {
            if (i >= 0 && i < tuple.length)
                return tuple[i];

            throw newIllegalArgumentException("The provided index %d is not within index bounds [0, %d)", i, tuple.length);
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(tuple, tuple.length);
        }

        @Override
        public List<TupleElement<?>> getElements() {
            return Collections.unmodifiableList(elements);
        }

        @Override
        public String toString() {
            return Arrays.toString(tuple);
        }

        @SuppressWarnings("unchecked")
        private <X> X uncheckedCast(Object o) {
            return (X) o;
        }

        private <X> X checkedCast(Object o, Class<X> clazz) {
            return clazz.cast(o);
        }

        private IllegalArgumentException newIllegalArgumentException(String format, Object... args) {
            return new IllegalArgumentException(String.format(format, (Object[]) args));
        }

    }
}
