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

import com.speedment.jpastreamer.field.StringField;
import com.speedment.jpastreamer.field.comparator.FieldComparator;
import com.speedment.jpastreamer.field.comparator.NullOrder;
import com.speedment.jpastreamer.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.jpastreamer.field.internal.predicate.reference.*;
import com.speedment.jpastreamer.field.internal.predicate.string.*;
import com.speedment.jpastreamer.field.internal.util.CollectionUtil;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class StringFieldImpl<ENTITY>
implements StringField<ENTITY>,
        FieldComparator<ENTITY> {

    private final Class<ENTITY> table;
    private final String columnName;
    private final ReferenceGetter<ENTITY, String> getter;
    private final boolean unique;

    public StringFieldImpl(
        final Class<ENTITY> table,
        final String columnName,
        final ReferenceGetter<ENTITY, String> getter,
        final boolean unique
    ) {
        this.table = requireNonNull(table);
        this.columnName = requireNonNull(columnName);
        this.getter     = requireNonNull(getter);
        this.unique     = unique;
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Class<ENTITY> table() {
        return table;
    }

    @Override
    public ReferenceGetter<ENTITY, String> getter() {
        return getter;
    }

    @Override
    public boolean isUnique() {
        return unique;
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
        final String f = get(first);
        final String s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public StringIsNullPredicate<ENTITY> isNull() {
        return new StringIsNullPredicate<>(this);
    }

    @Override
    public StringIsNotNullPredicate<ENTITY> isNotNull() {
        return new StringIsNotNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(String value) {
        requireNonNull(value);
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(String value) {
        requireNonNull(value);
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(String value) {
        requireNonNull(value);
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(String start, String end, Inclusion inclusion) {
        requireNonNull(start);
        requireNonNull(end);
        requireNonNull(inclusion);
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<String> values) {
        requireNonNull(values);
        return new ReferenceInPredicate<>(this, CollectionUtil.collectionToSet(values));
    }
    
    @Override
    public SpeedmentPredicate<ENTITY> notEqual(String value) {
        requireNonNull(value);
        return new ReferenceNotEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(String value) {
        requireNonNull(value);
        return new ReferenceLessThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(String value) {
        requireNonNull(value);
        return new ReferenceLessOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(String start, String end, Inclusion inclusion) {
        requireNonNull(start);
        requireNonNull(end);
        requireNonNull(inclusion);
        return new ReferenceNotBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<String> values) {
        requireNonNull(values);
        return new ReferenceNotInPredicate<>(this, CollectionUtil.collectionToSet(values));
    }

    @Override
    public SpeedmentPredicate<ENTITY> equalIgnoreCase(String value) {
        requireNonNull(value);
        return new StringEqualIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWith(String value) {
        requireNonNull(value);
        return new StringStartsWithPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWith(String value) {
        requireNonNull(value);
        return new StringEndsWithPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> contains(String value) {
        requireNonNull(value);
        return new StringContainsPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> isEmpty() {
        return new StringIsEmptyPredicate<>(this);
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWithIgnoreCase(String value) {
        requireNonNull(value);
        return new StringStartsWithIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWithIgnoreCase(String value) {
        requireNonNull(value);
        return new StringEndsWithIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> containsIgnoreCase(String value) {
        requireNonNull(value);
        return new StringContainsIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return StringFieldImpl.class.getSimpleName() + "{" + table.getSimpleName() + "}";
    }

    @Override
    public String columnName() {
        return columnName;
    }
}
