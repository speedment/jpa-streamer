package com.speedment.jpastreamer.analytics.standard.internal.google;

import java.util.Optional;

enum EventType {

    STARTED("jpastreamer-started", "start"),
    ALIVE("jpastreamer-alive", null),
    STOPPED("jpastreamer-stopped", "end");

    private final String eventName;
    private final String sessionControl;

    EventType(String eventName, String sessionControl) {
        this.eventName = eventName;
        this.sessionControl = sessionControl;
    }

    String eventName() {
        return eventName;
    }

    Optional<String> sessionControl() {
        return Optional.ofNullable(sessionControl);
    }

}