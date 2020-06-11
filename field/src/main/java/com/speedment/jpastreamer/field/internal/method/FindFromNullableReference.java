/*
 *
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
package com.speedment.jpastreamer.field.internal.method;

import com.speedment.jpastreamer.field.method.FindFromNullable;
import com.speedment.jpastreamer.field.trait.HasComparableOperators;
import com.speedment.jpastreamer.field.trait.HasReferenceOperators;
import com.speedment.jpastreamer.field.trait.HasReferenceValue;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.ReferenceField;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * Default implementation of {@link FindFromNullable} for
 * {@link ReferenceField ReferenceFields}.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class FindFromNullableReference<
    ENTITY, FK_ENTITY,
    V extends Comparable<? super V>,
    SOURCE extends Field<ENTITY>
                 & HasReferenceOperators<ENTITY>
                 & HasReferenceValue<ENTITY, ?, V>,
    TARGET extends Field<FK_ENTITY>
                 & HasComparableOperators<FK_ENTITY, V>
> extends AbstractFindFromNullable<ENTITY, FK_ENTITY, V, SOURCE, TARGET>
implements FindFromNullable<ENTITY, FK_ENTITY> {

    public FindFromNullableReference(
            SOURCE source,
            TARGET target,
            TableIdentifier<FK_ENTITY> foreignTable,
            Supplier<Stream<FK_ENTITY>> streamSupplier) {

        super(source, target, foreignTable, streamSupplier);
    }

    @Override
    public boolean isPresent(ENTITY entity) {
        return getSourceField().isNotNull().test(entity);
    }

    @Override
    public FK_ENTITY applyOrThrow(ENTITY entity) {

        return apply(entity).findAny()
            .orElseThrow(() -> new IllegalArgumentException(format(
                "Specified entity '%s' does not reference any %s.",
                entity, getTableIdentifier()
            )));
    }

    @Override
    public Stream<FK_ENTITY> apply(ENTITY entity) {
        return stream().filter(
            getTargetField().equal(getSourceField().get(entity))
        );
    }
}