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
package com.speedment.jpastreamer.field.expression;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.ToStringNullable;

import java.util.function.Function;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToStringNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 * @param <E>       the enum type mapped to
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToEnum<ENTITY, T, E extends Enum<E>>
extends FieldMapper<ENTITY, T, E, ToEnum<ENTITY, E>, Function<T, E>>,
        ToEnumNullable<ENTITY, E> {}