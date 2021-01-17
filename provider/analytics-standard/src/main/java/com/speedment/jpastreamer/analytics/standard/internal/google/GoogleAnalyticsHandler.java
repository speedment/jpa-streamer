/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.rest.Rest;
import com.speedment.jpastreamer.analytics.standard.internal.Handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Stream;

import static com.speedment.common.rest.Param.param;
import static com.speedment.common.rest.Rest.encode;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public final class GoogleAnalyticsHandler implements Handler {

    private static final String COOKIE_FILE_NAME = "JPAstreamer.clientid";
    private static final String URL_STRING = "www.google-analytics.com";
    private static final String TRACKING_ID = "UA-54384165-3";

    private final String version;
    private final boolean demoMode;
    private final String clientId;
    private final Random random;
    private final Rest analytics;

    public GoogleAnalyticsHandler(final String version, boolean demoMode) {
        this.version = requireNonNull(version);
        this.demoMode = demoMode;
        clientId = acquireClientId();
        random = new SecureRandom();
        analytics = Rest.connectHttps(URL_STRING);
    }

    @Override
    public void starting() {
        report(EventType.STARTED);
    }

    @Override
    public void running() {
        report(EventType.ALIVE);
    }

    @Override
    public void stopping() {
        report(EventType.STOPPED);
    }

    private void report(final EventType eventType) {
        requireNonNull(eventType);

        final String eventName = eventType.eventName() + (demoMode ? "-demo" : "");

        final StringJoiner payload = new StringJoiner("&")
                .add("v=" + encode("1")) // version. See https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters#v
                .add("ds=" + encode("speedment")) // data source. See https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters#ds
                .add("tid=" + encode(TRACKING_ID)) //
                .add("cid=" + clientId)
                //.add("uip=" + encode(event.getIpAddress()))
                //.add("ua=" + encode(event.getUserAgent()))
                .add("t=" + encode("screenview")) // Hit type
                .add("ni=" + encode("1")) // None interactive flag
                .add("cd=" + encode(eventName)) // Screen Name
                .add("an=" + encode("jpastreamer")) // Application Name
                .add("av=" + encode(version)); // Application version
        //.add("cd1=" + encode(event.getAppId().toString()))
        //.add("cd2=" + encode(event.getDatabases()))
        //.add("cd3=" + encode(event.getComputerName().orElse("no-host-specified")))
        //.add("cd4=" + encode(event.getEmailAddress().orElse("no-mail-specified")))

        eventType.sessionControl()
                .ifPresent(sc -> payload.add("sc=" + sc)); // Session control


        // System.out.println("Parameters: "+payload.toString());

        analytics.post("collect", payload.toString(),
                //header("User-Agent", event.getUserAgent()),
                param("z", Integer.toString(random.nextInt()))
        ).handle((res, ex) -> {
            if (ex != null) {
                System.err.println("Exception while sending usage statistics to Google Analytics.");
                ex.printStackTrace();
            } else if (!res.success()) {
                System.err.println("Exception while sending usage statistics to Google Analytics.");
                System.err.println(format("Google Analytics returned %d: %s",
                        res.getStatus(), res.getText()
                ));
            }

            return res;
        });
    }

    // This tries to read clientId from a "cookie" file in the
    // user's home directory. If that fails, a new random clientId
    // is generated and an attempt is made to save it in said file.
    private String acquireClientId() {
        final String userHome = System.getProperty("user.home");
        try {
            final Path path = Paths.get(userHome, COOKIE_FILE_NAME);
            try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
                return lines
                        .findFirst()
                        .map(UUID::fromString)
                        .orElseThrow(NoSuchElementException::new)
                        .toString();
            }
        } catch (Exception ignore) {
        }
        final String clientId = UUID.randomUUID().toString();
        try {
            final Path path = Paths.get(userHome, COOKIE_FILE_NAME);
            Files.write(path, clientId.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignore) {
        }
        return clientId;
    }

}