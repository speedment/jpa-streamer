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
package com.speedment.jpastreamer.field.exception;

/**
 * A specialization of {@code RuntimeException} that is thrown when something
 * is wrong with the bindings between fields.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SpeedmentFieldException extends RuntimeException {

    private static final long serialVersionUID = 4372472947892374L;

    public SpeedmentFieldException() {
    }

    public SpeedmentFieldException(String message) {
        super(message);
    }

    public SpeedmentFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentFieldException(Throwable cause) {
        super(cause);
    }

    public SpeedmentFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
