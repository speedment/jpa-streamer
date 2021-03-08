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

package com.speedment.jpastreamer.criteria.standard.internal;

import com.speedment.jpastreamer.criteria.QueryParameter;

import javax.persistence.criteria.ParameterExpression;

public class InternalQueryParameter<T> implements QueryParameter<T> {

    private final ParameterExpression<T> parameterExpression;
    private final T value;

    public InternalQueryParameter(ParameterExpression<T> parameterExpression, T value) {
        this.parameterExpression = parameterExpression;
        this.value = value;
    }
    
    @Override
    public ParameterExpression<T> getParameterExpression() {
        return parameterExpression;
    }

    @Override
    public T getValue() {
        return value;
    }
}
