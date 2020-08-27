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
package com.speedment.jpastreamer.field.internal.comparator;

import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.comparator.FieldComparator;
import com.speedment.jpastreamer.field.comparator.NullOrder;
import com.speedment.jpastreamer.field.comparator.ReferenceFieldComparator;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link ReferenceFieldComparator} interface.
 * 
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class ReferenceFieldComparatorImpl
    <ENTITY, V extends Comparable<? super V>>
extends AbstractFieldComparator<ENTITY>
implements ReferenceFieldComparator<ENTITY, V> {

    private final ComparableField<ENTITY, V> field;
    private final NullOrder nullOrder;
    private final boolean reversed;
    
    public ReferenceFieldComparatorImpl(
            final ComparableField<ENTITY, V> field,
            final NullOrder nullOrder) {

        this(field, nullOrder, false);
    }

    ReferenceFieldComparatorImpl(
            final ComparableField<ENTITY, V> field,
            final NullOrder nullOrder,
            final boolean reversed) {

        this.field     = requireNonNull(field);
        this.nullOrder = requireNonNull(nullOrder);
        this.reversed  = reversed;
    }

    @Override
    public ComparableField<ENTITY, V> getField() {
        return field;
    }

    @Override
    public NullOrder getNullOrder() {
        return nullOrder;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public FieldComparator<ENTITY> reversed() {
        return new ReferenceFieldComparatorImpl<>(field, nullOrder, !reversed);
    }

    @Override
    public int compare(ENTITY o1, ENTITY o2) {
        final V o1Value = field.get(requireNonNull(o1));
        final V o2Value = field.get(requireNonNull(o2));
        
        if (o1Value == null && o2Value == null) {
            if (NullOrder.NONE == nullOrder) {
                throw new NullPointerException(
                    "Both fields were null and null fields not allowed"
                );
            }
            return 0;
        } else if (o1Value == null) {
            return forNull(Parameter.FIRST);
        } else if (o2Value == null) {
            return forNull(Parameter.SECOND);
        }
        
        return applyReversed(o1Value.compareTo(o2Value));
    }
    
    @Override
    public int hashCode() {
        return ((4049 + Objects.hashCode(field.table())) * 3109
            + nullOrder.hashCode()) * 1039
            + Boolean.hashCode(reversed);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FieldComparator)) return false;
        
        @SuppressWarnings("unchecked")
        final FieldComparator<ENTITY> casted =
            (FieldComparator<ENTITY>) obj;
        
        return reversed  == casted.isReversed()
            && nullOrder == casted.getNullOrder()
            && Objects.equals(
                field.table(),
                casted.getField().table()
            );
    }
    
    @Override
    public String toString() {
        return "(order by " + field.table() + " " +
            (reversed ? "descending" : "ascending") + ")";
    }

    private int forNull(Parameter parameter) {
        final int firstOutcome = (Parameter.FIRST == parameter) ? -1 : 1;
        final int lastOutcome  = -firstOutcome;
        
        switch (nullOrder) {
            case FIRST : return applyReversed(firstOutcome);
            case LAST  : return applyReversed(lastOutcome);
            case NONE  : throw new NullPointerException(
                "A field was null and null fields not allowed"
            );
            default : throw new IllegalStateException("Illegal NullOrder");
        }
    }

    private int applyReversed(int compare) {
        return reversed ? -compare : compare;
    }
    
    private enum Parameter {
        FIRST, SECOND
    }
}