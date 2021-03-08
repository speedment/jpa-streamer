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
package com.speedment.jpastreamer.field.internal;

import com.speedment.jpastreamer.field.EnumField;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.comparator.FieldComparator;
import com.speedment.jpastreamer.field.comparator.NullOrder;
import com.speedment.jpastreamer.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.jpastreamer.field.internal.predicate.AlwaysFalsePredicate;
import com.speedment.jpastreamer.field.internal.predicate.enums.EnumIsNotNullPredicate;
import com.speedment.jpastreamer.field.internal.predicate.enums.EnumIsNullPredicate;
import com.speedment.jpastreamer.field.internal.predicate.reference.ReferenceEqualPredicate;
import com.speedment.jpastreamer.field.internal.predicate.reference.ReferenceInPredicate;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link EnumField}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public final class EnumFieldImpl<ENTITY, E extends Enum<E>>
    implements
    EnumField<ENTITY, E>,
        FieldComparator<ENTITY> {

    private final Class<ENTITY> table;
    private final String columnName;
    private final ReferenceGetter<ENTITY, E> getter;
    private final Class<E> enumClass;
    private final EnumSet<E> constants;

    public EnumFieldImpl(
            final Class<ENTITY> table,
            final String columnName,
            final ReferenceGetter<ENTITY, E> getter,
            final Class<E> enumClass
    ) {
        this.table = requireNonNull(table);
        this.columnName = requireNonNull(columnName);
        this.getter       = requireNonNull(getter);
        this.enumClass    = requireNonNull(enumClass);
        this.constants    = EnumSet.allOf(enumClass);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public EnumSet<E> constants() {
        return EnumSet.allOf(enumClass);
    }

    @Override
    public Class<ENTITY> table() {
        return table;
    }

    @Override
    public ReferenceGetter<ENTITY, E> getter() {
        return getter;
    }

    @Override
    public boolean isUnique() {
        return false;
    }


    ////////////////////////////////////////////////////////////////////////////
    //                              Comparators                               //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public FieldComparator<ENTITY> comparator() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.LAST);
    }

    @Override
    public FieldComparator<ENTITY> comparatorNullFieldsFirst() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.FIRST);
    }

    @Override
    public Field<ENTITY> getField() {
        return this;
    }

    @Override
    public NullOrder getNullOrder() {
        return NullOrder.LAST;
    }

    @Override
    public boolean isReversed() {
        return false;
    }

    @Override
    public FieldComparator<ENTITY> reversed() {
        return comparator().reversed();
    }

    @Override
    public int compare(ENTITY first, ENTITY second) {
        final E f = get(first);
        final E s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                              Predicates                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public EnumIsNullPredicate<ENTITY, E> isNull() {
        return new EnumIsNullPredicate<>(this);
    }

    @Override
    public EnumIsNotNullPredicate<ENTITY, E> isNotNull() {
        return new EnumIsNotNullPredicate<>(this);
    }

    @Override
    public SpeedmentPredicate<ENTITY> equal(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) == 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) != 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) < 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) <= 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) > 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) >= 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(E start, E end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) >  0 && e.compareTo(end) <  0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) >  0 && e.compareTo(end) <= 0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) >= 0 && e.compareTo(end) <  0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) >= 0 && e.compareTo(end) <= 0;
                default : throw newUnsupportedOperationException(inclusion);
            }
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(E start, E end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) <= 0 || e.compareTo(end) >= 0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) <= 0 || e.compareTo(end) >  0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) <  0 || e.compareTo(end) >= 0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) <  0 || e.compareTo(end) >  0;
                default : throw newUnsupportedOperationException(inclusion);
            }
        });
    }

    private UnsupportedOperationException newUnsupportedOperationException(Inclusion inclusion) {
        return new UnsupportedOperationException(
            "Unknown inclusion '" + inclusion + "'."
        );
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<E> values) {
        return toEntityPredicate(values::contains);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<E> values) {
        return toEntityPredicate(e -> !values.contains(e));
    }

    ////////////////////////////////////////////////////////////////////////////
    //                            Internal Methods                            //
    ////////////////////////////////////////////////////////////////////////////

    private SpeedmentPredicate<ENTITY> toEntityPredicate(Predicate<E> predicate) {
        final EnumSet<E> valid = evaluate(predicate);
        switch (valid.size()) {
            case 0  : return new AlwaysFalsePredicate<>(this);
            case 1  : return new ReferenceEqualPredicate<>(this, valid.iterator().next());
            default : return new ReferenceInPredicate<>(this, valid);
        }
    }

    private EnumSet<E> evaluate(Predicate<E> predicate) {
        final EnumSet<E> result = EnumSet.noneOf(enumClass);
        constants.stream().filter(predicate).forEach(result::add);
        return result;
    }

    @Override
    public String columnName() {
        return columnName;
    }
}
