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

package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import com.speedment.jpastreamer.streamconfiguration.StreamConfigurationBuilder;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class StandardStreamConfigurationBuilder<T> implements StreamConfigurationBuilder<T> {

    private final Class<T> entityClass;
    private final Set<Field<T>> joins;

    public StandardStreamConfigurationBuilder(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.joins = new HashSet<>();
    }

    @Override
    public StreamConfigurationBuilder<T> joining(final Field<T> field) {
        joins.add(requireNonNull(field));
        return this;
    }

    @Override
    public StreamConfiguration<T> build() {
        return new StandardStreamConfiguration<>(entityClass, joins);
    }
}
