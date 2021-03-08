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
