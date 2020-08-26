package com.speedment.jpastreamer.analytics;

public interface AnalyticsReporterFactory {

    AnalyticsReporter createAnalyticsReporter(String version);

}