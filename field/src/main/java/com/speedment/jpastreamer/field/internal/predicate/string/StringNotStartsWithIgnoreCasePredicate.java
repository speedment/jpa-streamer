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

import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

/**
 *
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class StringNotStartsWithIgnoreCasePredicate<ENTITY>
extends AbstractStringPredicate<ENTITY> {

    public StringNotStartsWithIgnoreCasePredicate(
            final HasReferenceValue<ENTITY, String> field,
            final String lowerCase) {

        super(PredicateType.NOT_STARTS_WITH_IGNORE_CASE, field, lowerCase, entity -> {
            final String fieldValue = field.get(entity);
            return fieldValue != null
                && !fieldValue.toLowerCase().startsWith(lowerCase);
        });
    }

    @Override
    public StringStartsWithIgnoreCasePredicate<ENTITY> negate() {
        return new StringStartsWithIgnoreCasePredicate<>(getField(), get0());
    }
}
