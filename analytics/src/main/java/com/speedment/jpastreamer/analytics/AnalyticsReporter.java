package com.speedment.jpastreamer.analytics;

public interface AnalyticsReporter {

    /**
     * Starts analytics reporting.
     *
     * @param version the JPA Streamer version
     */
    void start(String version);

    /**
     * Stops analytics reporting and releases
     * potential resources held by the component.
     */
    void stop();

}