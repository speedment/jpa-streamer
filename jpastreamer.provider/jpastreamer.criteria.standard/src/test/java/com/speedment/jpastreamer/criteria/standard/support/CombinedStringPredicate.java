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
package com.speedment.jpastreamer.criteria.standard.support;

import com.speedment.jpastreamer.field.predicate.CombinedPredicate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class CombinedStringPredicate implements CombinedPredicate<String> {

    private final List<Predicate<? super String>> predicates;
    private final Type type;

    public CombinedStringPredicate(final List<Predicate<? super String>> predicates) {
        this(predicates, Type.AND);
    }

    public CombinedStringPredicate(final List<Predicate<? super String>> predicates,
            final Type type) {
        this.predicates = predicates;
        this.type = type;
    }

    @Override
    public Stream<Predicate<? super String>> stream() {
        return predicates.stream();
    }

    @Override
    public int size() {
        return predicates.size();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public CombinedPredicate<String> and(Predicate<? super String> other) {
        return null;
    }

    @Override
    public CombinedPredicate<String> or(Predicate<? super String> other) {
        return null;
    }

    @Override
    public CombinedPredicate<String> negate() {
        return null;
    }

    @Override
    public boolean applyAsBoolean(String s) {
        return false;
    }
}
