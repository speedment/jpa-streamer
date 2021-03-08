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
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import static com.speedment.jpastreamer.field.predicate.PredicateType.IS_NULL;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceIsNullPredicate<ENTITY, V>
extends AbstractFieldPredicate<ENTITY,
        HasReferenceValue<ENTITY, V>> {

    public ReferenceIsNullPredicate(HasReferenceValue<ENTITY, V> field) {
        super(IS_NULL, field, entity -> entity == null || field.get(entity) == null);
    }

    @Override
    public ReferenceIsNotNullPredicate<ENTITY, V> negate() {
        return new ReferenceIsNotNullPredicate<>(getField());
    }
}
