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
package com.speedment.jpastreamer.builder.standard.internal;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

enum StreamBuilderUtil {;

    static final String MSG_STREAM_LINKED_CONSUMED_OR_CLOSED = "Stream has already been linked, consumed or closed";

    static void runAll(Collection<Runnable> runnables) {
        requireNonNull(runnables);
        Exception exception = null;
        for (Runnable r : runnables) {
            try {
                r.run();
            } catch (Exception e) {
                if (exception == null)
                    exception = e;
            }
        }
        if (exception != null)
            if (exception instanceof RuntimeException) {
                final RuntimeException runtimeException = (RuntimeException) exception;
                throw runtimeException;
            } else {
                throw new RuntimeException(exception);
            }
    }

}
