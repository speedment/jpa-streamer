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
package com.speedment.jpastreamer.field.expression;

import com.speedment.common.function.ToByteFunction;
import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToByteNullable;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToByteNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToByte<ENTITY, T>
extends FieldMapper<ENTITY, T, Byte, ToByte<ENTITY>, ToByteFunction<T>>,
        ToByteNullable<ENTITY> {}
