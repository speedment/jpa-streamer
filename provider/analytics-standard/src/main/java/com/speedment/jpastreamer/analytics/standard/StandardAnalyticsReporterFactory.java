package com.speedment.jpastreamer.analytics.standard;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.analytics.AnalyticsReporterFactory;
import com.speedment.jpastreamer.analytics.standard.internal.google.GoogleAnalyticsHandler;
import com.speedment.jpastreamer.analytics.standard.internal.InternalStandardAnalyticsReporter;

public final class StandardAnalyticsReporterFactory implements AnalyticsReporterFactory {

    @Override
    public AnalyticsReporter createAnalyticsReporter(final String version) {
        return new InternalStandardAnalyticsReporter(new GoogleAnalyticsHandler(version));
    }
}
