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

package com.speedment.jpastreamer.criteria.standard.internal.predicate;

import static java.util.Objects.requireNonNull;

import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ParameterizedPredicate<K, S> {

    private final BiFunction<K, ParameterExpression<S>, Predicate> parameterMapper;
    private final Function<S, S> valueMapper;

    private ParameterizedPredicate(
        final BiFunction<K, ParameterExpression<S>, Predicate> parameterMapper
    ) {
        this(parameterMapper, Function.identity());
    }

    private ParameterizedPredicate(
        final BiFunction<K, ParameterExpression<S>, Predicate> parameterMapper,
        final Function<S, S> valueMapper
    ) {
        this.parameterMapper = requireNonNull(parameterMapper);
        this.valueMapper = requireNonNull(valueMapper);
    }

    public BiFunction<K, ParameterExpression<S>, Predicate> getParameterMapper() {
        return parameterMapper;
    }

    public Function<S, S> getValueMapper() {
        return valueMapper;
    }

    public static <K, S> ParameterizedPredicate<K, S> createParameterizedPredicate(
        final BiFunction<K, ParameterExpression<S>, Predicate> parameterMapper
    ) {
        return new ParameterizedPredicate<>(parameterMapper);
    }

    public static <K, S> ParameterizedPredicate<K, S> createParameterizedPredicate(
        final BiFunction<K, ParameterExpression<S>, Predicate> parameterMapper,
        final Function<S, S> valueMapper
    ) {
        return new ParameterizedPredicate<>(parameterMapper, valueMapper);
    }
}
