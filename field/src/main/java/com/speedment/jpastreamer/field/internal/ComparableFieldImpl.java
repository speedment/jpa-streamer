/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.jpastreamer.field.internal;

import com.speedment.jpastreamer.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.jpastreamer.field.internal.predicate.reference.*;
import com.speedment.jpastreamer.field.internal.util.CollectionUtil;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.comparator.FieldComparator;
import com.speedment.jpastreamer.field.comparator.NullOrder;
import com.speedment.jpastreamer.field.method.ReferenceGetter;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import javax.persistence.AttributeConverter;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <V>      the field value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * 
 * @since   2.2.0
 */
public final class ComparableFieldImpl<ENTITY, D, V extends Comparable<? super V>>
implements ComparableField<ENTITY, D, V>, FieldComparator<ENTITY> {

    private final Class<ENTITY> table;
    private final String columnName;
    private final ReferenceGetter<ENTITY, V> getter;
    private final Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass;
    private final boolean unique;

    public ComparableFieldImpl(
            Class<ENTITY> table,
            String columnName,
            ReferenceGetter<ENTITY, V> getter,
            Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass,
            boolean unique) {
        
        this.table = requireNonNull(table);
        this.columnName = requireNonNull(columnName);
        this.getter     = requireNonNull(getter);
        this.attributeConverterClass = attributeConverterClass;
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
    public ReferenceGetter<ENTITY, V> getter() {
        return getter;
    }

    @Override
    public Class<? extends AttributeConverter<? super V, ? super D>> attributeConverterClass() {
        return attributeConverterClass;
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
        final V f = get(first);
        final V s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public FieldPredicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(V value) {
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEqual(V value) {
        return new ReferenceNotEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(V value) {
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(V value) {
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(V value) {
        return new ReferenceLessThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(V value) {
        return new ReferenceLessOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(V start, V end, Inclusion inclusion) {
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(V start, V end, Inclusion inclusion) {
        return new ReferenceNotBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<V> values) {
        return new ReferenceInPredicate<>(this, CollectionUtil.collectionToSet(values));
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<V> values) {
        return new ReferenceNotInPredicate<>(this, CollectionUtil.collectionToSet(values));
    }

    @Override
    public String columnName() {
        return columnName;
    }
}