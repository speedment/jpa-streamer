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
package com.speedment.jpastreamer.criteria;

import javax.persistence.criteria.ParameterExpression;

/**
 * Stores a query parameter and the value associated with the parameter
 *
 * @param <T> parameter type
 * @author Mislav Milicevic
 */
public interface QueryParameter<T> {

    ParameterExpression<T> getParameterExpression();

    T getValue();

}
