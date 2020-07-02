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
