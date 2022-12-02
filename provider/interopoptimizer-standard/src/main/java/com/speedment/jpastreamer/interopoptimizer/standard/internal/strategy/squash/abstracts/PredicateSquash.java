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
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.abstracts;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public abstract class PredicateSquash<T> extends AbstractSingleValueSquash<Predicate<T>> {

    @Override
    @SuppressWarnings("unchecked")
    public Class<Predicate<T>> valueClass() {
        final Predicate<T> $ = x -> true;
        return (Class<Predicate<T>>) $.getClass().getInterfaces()[0];
    }

    @Override
    public Predicate<T> initialValue() {
        return null;
    }

    @Override
    public BiFunction<Predicate<T>, Predicate<T>, Predicate<T>> squash() {
        return (value, result) -> {
            if (result == null) {
                return value;
            }

            return value.and(result);
        };
    }
}
