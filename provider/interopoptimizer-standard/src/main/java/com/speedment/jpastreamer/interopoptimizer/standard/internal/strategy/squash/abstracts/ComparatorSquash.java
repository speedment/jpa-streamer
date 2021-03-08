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
package com.speedment.jpastreamer.interopoptimizer.standard.internal.strategy.squash.abstracts;

import java.util.Comparator;
import java.util.function.BiFunction;

public abstract class ComparatorSquash<T> extends AbstractSingleValueSquash<Comparator<T>> {

    @Override
    @SuppressWarnings("unchecked")
    public Class<Comparator<T>> valueClass() {
        final Comparator<T> $ = (o1, o2) -> 0;
        return (Class<Comparator<T>>) $.getClass().getInterfaces()[0];
    }

    @Override
    public Comparator<T> initialValue() {
        return null;
    }

    @Override
    public BiFunction<Comparator<T>, Comparator<T>, Comparator<T>> squash() {
        return (value, result) -> {
            if (result == null) {
                return value;
            }

            return value.thenComparing(result);
        };
    }
}
