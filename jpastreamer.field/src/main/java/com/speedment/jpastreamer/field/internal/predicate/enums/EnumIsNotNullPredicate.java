/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.field.internal.predicate.enums;

import com.speedment.jpastreamer.field.EnumField;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;
import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class EnumIsNotNullPredicate<ENTITY, E extends Enum<E>>
implements FieldIsNotNullPredicate<ENTITY, E> {

    private final EnumField<ENTITY, E> field;

    public EnumIsNotNullPredicate(EnumField<ENTITY, E> field) {
        this.field = requireNonNull(field);
    }

    @Override
    public boolean test(ENTITY value) {
        return field.apply(value) != null;
    }

    @Override
    public FieldIsNullPredicate<ENTITY, E> negate() {
        return new EnumIsNullPredicate<>(field);
    }

    @Override
    public ToNullable<ENTITY, E, ?> expression() {
        return field;
    }

    @Override
    public EnumField<ENTITY, E> getField() {
        return field;
    }
}
