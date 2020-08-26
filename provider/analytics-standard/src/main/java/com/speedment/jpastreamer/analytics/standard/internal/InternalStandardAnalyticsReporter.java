package com.speedment.jpastreamer.analytics.standard.internal;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

public final class InternalStandardAnalyticsReporter implements AnalyticsReporter {

    private static final long ALIVE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(1);
    private static final AtomicInteger THREAD_COUNT = new AtomicInteger();

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, this::threadFactory);
    private final AtomicBoolean started = new AtomicBoolean();
    private final AtomicBoolean stopped = new AtomicBoolean();
    private final Handler handler;

    public InternalStandardAnalyticsReporter(final Handler handler) {
        this.handler = requireNonNull(handler);
    }

    @Override
    public void start() {
        if (!stopped.get() && started.compareAndSet(false, true)) {
            executorService.schedule(handler::starting, 50, TimeUnit.MILLISECONDS);
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
            } catch (InterruptedException ignore) {}
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