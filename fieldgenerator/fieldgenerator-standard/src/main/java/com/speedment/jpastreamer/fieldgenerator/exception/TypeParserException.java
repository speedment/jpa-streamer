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
package com.speedment.jpastreamer.fieldgenerator.exception;

/**
 * A specialized {@code RuntimeException} used to signal errors during the type parsing process.
 *
 * @author Julia Gustafsson
 * @since 0.1.0
 */

public class TypeParserException extends RuntimeException {

    public TypeParserException(String message) {
        super(message);
    }

}
