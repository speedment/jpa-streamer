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
package com.speedment.jpastreamer.fieldgenerator.exception;

/**
 * A specialized {@code RuntimeException} used to signal errors during the generation process of JPAStreamer fields.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

public class FieldGeneratorProcessorException extends RuntimeException {

    public FieldGeneratorProcessorException() {}

    public FieldGeneratorProcessorException(String message) {
        super(message);
    }

    public FieldGeneratorProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldGeneratorProcessorException(Throwable cause) {
        super(cause);
    }

    public FieldGeneratorProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
