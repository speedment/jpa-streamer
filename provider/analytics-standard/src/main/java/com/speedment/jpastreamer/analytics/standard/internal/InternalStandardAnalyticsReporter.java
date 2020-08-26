package com.speedment.jpastreamer.analytics.standard.internal;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;

import static java.util.Objects.requireNonNull;

public final class InternalStandardAnalyticsReporter implements AnalyticsReporter {

    @Override
    public void start(final String version) {
        requireNonNull(version);
        System.out.println(InternalStandardAnalyticsReporter.class.getSimpleName() + ".start(" + version + ")");
    }

    @Override
    public void stop() {
        System.out.println(InternalStandardAnalyticsReporter.class.getSimpleName() + ".stop()");
    }
}