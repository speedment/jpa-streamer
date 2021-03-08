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
package com.speedment.jpastreamer.field.internal.predicate.reference;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import java.util.Set;

import static com.speedment.jpastreamer.field.predicate.PredicateType.NOT_IN;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceNotInPredicate<ENTITY, V extends Comparable<? super V>>
        extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, V>>
        implements HasArg0<Set<V>> {

    private final Set<V> set;

    public ReferenceNotInPredicate(HasReferenceValue<ENTITY, V> field, Set<V> values) {
        super(NOT_IN, field, entity -> {
            final V value = field.get(entity);
            return value != null && !values.contains(value);
        });
        this.set = requireNonNull(values);
    }

    @Override
    public Set<V> get0() {
        return set;
    }

    @Override
    public ReferenceInPredicate<ENTITY, V> negate() {
        return new ReferenceInPredicate<>(getField(), set);
    }
}
