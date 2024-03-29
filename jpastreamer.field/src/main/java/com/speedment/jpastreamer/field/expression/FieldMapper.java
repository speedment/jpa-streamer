/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.jpastreamer.field.ReferenceField;

/**
 * Specialized expression that takes the value of a particular Speedment field
 * and maps it to a specific type.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type mapped from
 * @param <R>       the type mapped to
 * @param <NON_NULLABLE>  the expression type obtained if the nullability of
 *                        this expression is handled
 * @param <MAPPER>  functional interface type returned by {@link #getMapper()}
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldMapper<ENTITY, T, R, NON_NULLABLE extends Expression<ENTITY>, MAPPER>
extends Expression<ENTITY>, ToNullable<ENTITY, R, NON_NULLABLE> {

    /**
     * The field that is being mapped.
     *
     * @return  the field
     */
    ReferenceField<ENTITY, T> getField();

    /**
     * Returns the functional interface implementation that is used when doing
     * the mapping. The returned object must implement one of the following
     * interfaces:
     * <ul>
     *     <li>{@link com.speedment.common.function.ToByteFunction}
     *     <li>{@link com.speedment.common.function.ToShortFunction}
     *     <li>{@link java.util.function.ToIntFunction}
     *     <li>{@link java.util.function.ToLongFunction}
     *     <li>{@link java.util.function.ToDoubleFunction}
     *     <li>{@link com.speedment.common.function.ToFloatFunction}
     *     <li>{@link com.speedment.common.function.ToCharFunction}
     *     <li>{@link com.speedment.common.function.ToBooleanFunction}
     *     <li>{@link java.util.function.Function}
     * </ul>
     * The first generic type should be the boxed type returned by the getters
     * of the {@link #getField() field}. If the mapper implements
     * {@link java.util.function.Function}, then the returning type must be
     * either {@link String}, {@link Enum} or
     * {@link java.math.BigDecimal}.
     *
     * @return  the mapper to use
     */
    MAPPER getMapper();

}
