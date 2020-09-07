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
package com.speedment.jpastreamer.field.exception;

public class IllegalJavaBeanException extends RuntimeException {

    private static final long serialVersionUID = 92362727623423324L;

    public IllegalJavaBeanException(final Class<?> clazz, final String fieldName) {
        super(String.format(
                "The field '%s.%s' could not be matched to any getter. Please update your %s class to reflect standard JavaBean notation.",
                clazz.getName(), fieldName, clazz.getName()
        ));
    }

}