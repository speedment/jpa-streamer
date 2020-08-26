package com.speedment.jpastreamer.analytics.standard.internal;

import static java.util.Objects.requireNonNull;

public final class GoogleAnalyticsHandler implements Handler {

    private final String version;

    public GoogleAnalyticsHandler(final String version) {
        this.version = requireNonNull(version);
    }

    @Override
    public void starting() {
        System.out.println(GoogleAnalyticsHandler.class.getSimpleName() + ".starting(" + version + ")");
    }

    @Override
    public void running() {
        System.out.println(GoogleAnalyticsHandler.class.getSimpleName() + ".running(" + version + ")");
    }

    @Override
    public void stopping() {
        System.out.println(GoogleAnalyticsHandler.class.getSimpleName() + ".stopping(" + version + ")");
    }
}