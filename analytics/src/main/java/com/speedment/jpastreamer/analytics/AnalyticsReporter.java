package com.speedment.jpastreamer.analytics;

public interface AnalyticsReporter {

    /**
     * Starts analytics reporting.
     *
     */
    void start();

    /**
     * Stops analytics reporting and releases
     * potential resources held by the component.
     */
    void stop();

}