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

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that contains meta-data about the {@link Field} that was used to
 * construct it.
 *
 * @param <ENTITY> the entity type that is being tested
 * @param <FIELD>  the field in the entity that is operated on
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public abstract class AbstractFieldPredicate<ENTITY, FIELD extends Field<ENTITY>>
extends AbstractPredicate<ENTITY>
implements FieldPredicate<ENTITY> {

    private final PredicateType predicateType;
    private final FIELD field;
    private final Predicate<ENTITY> tester;

    protected AbstractFieldPredicate(
            final PredicateType predicateType,
            final FIELD field,
            final Predicate<ENTITY> tester) {

        this.predicateType = requireNonNull(predicateType);
        this.field         = requireNonNull(field);
        this.tester        = requireNonNull(tester);
    }

    protected final Predicate<ENTITY> getTester() {
        return tester;
    }

    @Override
    public boolean applyAsBoolean(ENTITY instance) {
        return tester.test(instance);
    }

    @Override
    public final PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public final FIELD getField() {
        return field;
    }


    @Override
    public String toString() {
        final Class<ENTITY> table = field.table();
        final StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName())
            .append(" {")
            .append("table: ")
            .append(table.getName()).append('.')
            .append(", type: '").append(predicateType).append("'");

        return sb.append("}").toString();
    }
}
