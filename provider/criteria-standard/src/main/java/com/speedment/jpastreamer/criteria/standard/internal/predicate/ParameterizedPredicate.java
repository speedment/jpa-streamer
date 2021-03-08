/*
 * Copyright (c) 2006-2021, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
