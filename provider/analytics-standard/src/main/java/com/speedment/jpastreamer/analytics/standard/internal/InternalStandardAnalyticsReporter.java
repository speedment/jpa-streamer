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
package com.speedment.jpastreamer.analytics.standard.internal;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

public final class InternalStandardAnalyticsReporter implements AnalyticsReporter {

    private static final long ALIVE_INTERVAL_MS = TimeUnit.MINUTES.toMillis(20);
    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    private final ScheduledExecutorService executorService;
    private final AtomicBoolean started;
    private final AtomicBoolean stopped;
    private final Handler handler;

    public InternalStandardAnalyticsReporter(final Handler handler) {
        this.handler = requireNonNull(handler);
        executorService = Executors.newScheduledThreadPool(1, this::threadFactory);
        started = new AtomicBoolean();
        stopped = new AtomicBoolean();
    }

    @Override
    public void start() {
        if (!stopped.get() && started.compareAndSet(false, true)) {
            executorService.schedule(handler::starting, 1, TimeUnit.MILLISECONDS);
            executorService.scheduleAtFixedRate(handler::running, ALIVE_INTERVAL_MS, ALIVE_INTERVAL_MS, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void stop() {
        if (started.get() && stopped.compareAndSet(false, true)) {
            executorService.execute(handler::stopping);
            try {
                Thread.sleep(10);
                executorService.shutdown();
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.out.println("Warning: Unable to shutdown analytics reporter.");
                }
            } catch (InterruptedException ignore) {
                // Todo: re-interrupt or re-throw
            }
            executorService.shutdownNow();
        }
    }

    private Thread threadFactory(final Runnable runnable) {
        requireNonNull(runnable);
        final Thread thread = new Thread(runnable, InternalStandardAnalyticsReporter.class.getSimpleName() + "-" + THREAD_COUNT.getAndIncrement());
        thread.setDaemon(true);
        return thread;
    }

}
