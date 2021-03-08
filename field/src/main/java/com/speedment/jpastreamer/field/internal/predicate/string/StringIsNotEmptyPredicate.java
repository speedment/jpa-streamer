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
package com.speedment.jpastreamer.field.internal.predicate.string;

import com.speedment.jpastreamer.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

import static com.speedment.jpastreamer.field.predicate.PredicateType.IS_NOT_EMPTY;

/**
 *
 * @param <ENTITY> the entity type
 *
 * @author  Emil Forslund
 * @since   3.0.11
 */
public final class StringIsNotEmptyPredicate<ENTITY>
extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, String>> {

    public StringIsNotEmptyPredicate(
            final HasReferenceValue<ENTITY, String> field) {

        super(IS_NOT_EMPTY, field, entity -> {
            final String result = field.get(entity);
            return result != null && !result.isEmpty();
        });
    }

    @Override
    public StringIsEmptyPredicate<ENTITY> negate() {
        return new StringIsEmptyPredicate<>(getField());
    }
}
