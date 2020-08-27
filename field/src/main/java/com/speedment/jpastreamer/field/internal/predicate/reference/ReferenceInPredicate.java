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
package com.speedment.jpastreamer.field.internal.predicate.reference;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import java.util.Set;

import static com.speedment.jpastreamer.field.predicate.PredicateType.IN;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceInPredicate<ENTITY, V extends Comparable<? super V>>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, V>>
implements HasArg0<Set<V>> {

    private final Set<V> set;

    public ReferenceInPredicate(HasReferenceValue<ENTITY, V> field, Set<V> values) {
        super(IN, field, entity -> values.contains(field.get(entity)));
        this.set = requireNonNull(values);
    }

    @Override
    public Set<V> get0() {
        return set;
    }

    @Override
    public ReferenceNotInPredicate<ENTITY, V> negate() {
        return new ReferenceNotInPredicate<>(getField(), set);
    }
}