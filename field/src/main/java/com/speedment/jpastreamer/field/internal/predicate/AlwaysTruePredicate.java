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
package com.speedment.jpastreamer.field.internal.predicate;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.PredicateType;

/**
 * A special implementation of {@code Predicate} that always returns true.
 *
 * @param <ENTITY> the entity type
 * @param <FIELD> the field type
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public final class AlwaysTruePredicate<ENTITY, FIELD extends Field<ENTITY>>
    extends AbstractFieldPredicate<ENTITY, FIELD> {

    public AlwaysTruePredicate(FIELD field) {
        super(PredicateType.ALWAYS_TRUE, field, entity -> true);
    }

    @Override
    public FieldPredicate<ENTITY> negate() {
        return new AlwaysFalsePredicate<>(getField());
    }

}
