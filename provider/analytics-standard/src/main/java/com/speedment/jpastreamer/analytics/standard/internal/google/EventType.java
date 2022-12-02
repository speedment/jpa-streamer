/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
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
