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

import com.speedment.jpastreamer.field.StringField;
import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.FieldIsNotNullPredicate;
import com.speedment.jpastreamer.field.predicate.FieldIsNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class StringIsNullPredicate<ENTITY>
implements FieldIsNullPredicate<ENTITY, String> {

    private final StringField<ENTITY> field;

    public StringIsNullPredicate(StringField<ENTITY> field) {
        this.field = requireNonNull(field);
    }

    @Override
    public boolean test(ENTITY value) {
        return field.apply(value) == null;
    }

    @Override
    public FieldIsNotNullPredicate<ENTITY, String> negate() {
        return new StringIsNotNullPredicate<>(field);
    }

    @Override
    public ToNullable<ENTITY, String, ?> expression() {
        return field;
    }

    @Override
    public Field<ENTITY> getField() {
        return field;
    }
}
