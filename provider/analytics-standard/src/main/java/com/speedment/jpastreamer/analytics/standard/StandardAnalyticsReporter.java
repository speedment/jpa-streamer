package com.speedment.jpastreamer.analytics.standard;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.analytics.standard.internal.InternalStandardAnalyticsReporter;

public final class StandardAnalyticsReporter implements AnalyticsReporter {

    private final AnalyticsReporter delegate = new InternalStandardAnalyticsReporter();

    @Override
    public void start(String version) {
        delegate.start(version);
    }

    @Override
    public void stop() {
        delegate.stop();
    }
}