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
package com.speedment.jpastreamer.field.internal.predicate.string;

import com.speedment.jpastreamer.field.predicate.PredicateType;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;

/**
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public final class StringStartsWithPredicate<ENTITY>
extends AbstractStringPredicate<ENTITY> {

    public StringStartsWithPredicate(
            final HasReferenceValue<ENTITY, String> field,
            final String str) {

        super(PredicateType.STARTS_WITH, field, str, entity -> {
            final String fieldValue = field.get(entity);
            return fieldValue != null
                && fieldValue.startsWith(str);
        });
    }

    @Override
    public StringNotStartsWithPredicate<ENTITY> negate() {
        return new StringNotStartsWithPredicate<>(getField(), get0());
    }
}
