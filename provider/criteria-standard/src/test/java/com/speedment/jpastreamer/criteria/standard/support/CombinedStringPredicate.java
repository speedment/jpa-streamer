/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
