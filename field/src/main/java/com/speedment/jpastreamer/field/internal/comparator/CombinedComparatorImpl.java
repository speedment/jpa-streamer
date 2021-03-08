/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.field.internal.comparator;

import com.speedment.jpastreamer.field.comparator.FieldComparator;
import com.speedment.jpastreamer.field.comparator.NullOrder;
import com.speedment.jpastreamer.field.method.*;
import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.comparator.CombinedComparator;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link CombinedComparator}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class CombinedComparatorImpl<ENTITY>
implements CombinedComparator<ENTITY> {

    private final List<FieldComparator<? super ENTITY>> comparators;

    public CombinedComparatorImpl(
            final List<FieldComparator<? super ENTITY>> comparators) {

        this.comparators = requireNonNull(comparators);
    }

    @Override
    public Stream<FieldComparator<? super ENTITY>> stream() {
        return comparators.stream();
    }

    @Override
    public int size() {
        return comparators.size();
    }

    @Override
    public int compare(ENTITY o1, ENTITY o2) {
        for (final FieldComparator<? super ENTITY> comp : comparators) {
            final int c = comp.compare(o1, o2);
            if (c != 0) return c;
        }

        return 0;
    }

    @Override
    public CombinedComparator<ENTITY> reversed() {
        return new CombinedComparatorImpl<>(comparators.stream()
            .map(FieldComparator::reversed)
            .collect(Collectors.toList())
        );
    }

    @Override
    public Comparator<ENTITY> thenComparing(Comparator<? super ENTITY> other) {
        return then(other);
    }

    @Override
    public <U> Comparator<ENTITY> thenComparing(
            final Function<? super ENTITY, ? extends U> keyExtractor,
            final Comparator<? super U> keyComparator) {

        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                return keyComparator.compare(
                    keyExtractor.apply(a),
                    keyExtractor.apply(b)
                );
            } else return c;
        };
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<ENTITY>
    thenComparing(Function<? super ENTITY, ? extends U> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final U oa = keyExtractor.apply(a);
                final U ob = keyExtractor.apply(b);
                if (oa == null && ob == null) {
                    return 0;
                } else if (oa == null) {
                    return 1;
                } else if (ob == null) {
                    return -1;
                } else {
                    return oa.compareTo(ob);
                }
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingInt(ToIntFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final int oa = keyExtractor.applyAsInt(a);
                final int ob = keyExtractor.applyAsInt(b);
                return Integer.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingLong(ToLongFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final long oa = keyExtractor.applyAsLong(a);
                final long ob = keyExtractor.applyAsLong(b);
                return Long.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public Comparator<ENTITY>
    thenComparingDouble(ToDoubleFunction<? super ENTITY> keyExtractor) {
        if (keyExtractor instanceof Getter) {
            @SuppressWarnings("unchecked")
            final Getter<ENTITY> getter = (Getter<ENTITY>) keyExtractor;
            final Optional<Comparator<ENTITY>> result = then(getter);
            if (result.isPresent()) {
                return result.get();
            }
        }

        return (a, b) -> {
            final int c = compare(a, b);
            if (c == 0) {
                final double oa = keyExtractor.applyAsDouble(a);
                final double ob = keyExtractor.applyAsDouble(b);
                return Double.compare(oa, ob);
            } else return c;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombinedComparator)) return false;

        final CombinedComparator<?> that = (CombinedComparator<?>) o;
        final Iterator<FieldComparator<? super ENTITY>> it =
            comparators.iterator();

        return that.stream().allMatch(
            c -> it.hasNext() && it.next().equals(c)
        ) && !it.hasNext();
    }

    @Override
    public int hashCode() {
        return 1299827 * comparators.hashCode();
    }

    @Override
    public String toString() {
        return "CombinedComparatorImpl" + comparators;
    }

    private Comparator<ENTITY> then(Comparator<? super ENTITY> other) {
        if (other instanceof FieldComparator) {
            @SuppressWarnings("unchecked")
            final FieldComparator<? super ENTITY> fc =
                (FieldComparator<? super ENTITY>) other;

            final List<FieldComparator<? super ENTITY>> copy =
                new ArrayList<>(comparators);

            copy.add(fc);

            return new CombinedComparatorImpl<>(copy);
        } else if (other instanceof CombinedComparator) {
            @SuppressWarnings("unchecked")
            final CombinedComparator<? super ENTITY> cc =
                (CombinedComparator<? super ENTITY>) other;

            final List<FieldComparator<? super ENTITY>> copy =
                new ArrayList<>(comparators);

            cc.stream().forEachOrdered(copy::add);

            return new CombinedComparatorImpl<>(copy);
        } else {
            return (a, b) -> {
                final int c = compare(a, b);
                return c == 0 ? other.compare(a, b) : c;
            };
        }
    }

    private <D, V extends Comparable<? super V>>
    Optional<Comparator<ENTITY>> then(Getter<? super ENTITY> getter) {

        if (getter instanceof GetByte) {
            @SuppressWarnings("unchecked")
            final GetByte<ENTITY> casted = (GetByte<ENTITY>) getter;
            return Optional.of(then(new ByteFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetShort) {
            @SuppressWarnings("unchecked")
            final GetShort<ENTITY> casted = (GetShort<ENTITY>) getter;
            return Optional.of(then(new ShortFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetInt) {
            @SuppressWarnings("unchecked")
            final GetInt<ENTITY> casted = (GetInt<ENTITY>) getter;
            return Optional.of(then(new IntFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetLong) {
            @SuppressWarnings("unchecked")
            final GetLong<ENTITY> casted = (GetLong<ENTITY>) getter;
            return Optional.of(then(new LongFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetFloat) {
            @SuppressWarnings("unchecked")
            final GetFloat<ENTITY> casted = (GetFloat<ENTITY>) getter;
            return Optional.of(then(new FloatFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetDouble) {
            @SuppressWarnings("unchecked")
            final GetDouble<ENTITY> casted = (GetDouble<ENTITY>) getter;
            return Optional.of(then(new DoubleFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetChar) {
            @SuppressWarnings("unchecked")
            final GetChar<ENTITY> casted = (GetChar<ENTITY>) getter;
            return Optional.of(then(new CharFieldComparatorImpl<>(
                casted.getField())
            ));
        } else if (getter instanceof GetReference) {
            @SuppressWarnings("unchecked")
            final GetReference<ENTITY, V> casted = (GetReference<ENTITY, V>) getter;
            final Field<ENTITY> field = casted.getField();
            if (field instanceof ComparableField) {
                return Optional.of(then(new ReferenceFieldComparatorImpl<>(
                    (ComparableField<ENTITY, V>) casted.getField(),
                    NullOrder.LAST
                )));
            }
        }

        return Optional.empty();
    }
}
