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
                exception = e;
            }
        }
        if (exception != null)
            if (exception instanceof RuntimeException) {
                RuntimeException runtimeException = (RuntimeException) exception;
                throw runtimeException;
            } else {
                throw new RuntimeException(exception);
            }

    }


}