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

import com.speedment.common.rest.Rest;
import com.speedment.jpastreamer.analytics.standard.internal.Handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.jpastreamer.analytics.standard.internal.google.HttpUtil.urlEncode;
import static com.speedment.jpastreamer.analytics.standard.internal.google.JsonUtil.asElement;
import static com.speedment.jpastreamer.analytics.standard.internal.google.JsonUtil.jsonElement;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public final class GoogleAnalyticsHandler implements Handler {

    private static final String COOKIE_FILE_NAME = "JPAstreamer.clientid";
    private static final String URL_STRING = "https://www.google-analytics.com/mp/collect"; 
    private static final String MEASUREMENT_ID = "G-LNCF0RTS4N"; // JPAStreamer App Measurement ID
    private static final String API_SECRET = "J-EHimWhT8anCwaHfq-h-Q"; 

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
        final Map<String, String> eventParameters = new HashMap<>(); 
        final Map<String, String> userProperties = new HashMap<>(); 
        eventParameters.put("app_version", this.version);
        
        httpSend(eventName, eventParameters);
    }
    
    void httpSend(String eventName, final Map<String, String> eventParameters) {
        requireNonNull(eventName); 
        requireNonNull(eventParameters); 
        final String url = URL_STRING + "?measurement_id=" + urlEncode(MEASUREMENT_ID) + "&api_secret=" + urlEncode(API_SECRET);
        final String json = jsonFor(eventName, acquireClientId(), eventParameters);
        HttpUtil.send(url, json);
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

    static String jsonFor(final String eventName,
                          final String clientId) {
        requireNonNull(eventName);
        requireNonNull(clientId);
        return Stream.of(
                "{",
                jsonElement(" ", "clientId", clientId) + ',',
                jsonElement(" ", "userId", clientId) + ',',
                jsonElement(" ", "nonPersonalizedAds", true) + ',',
                ' ' + asElement("events") + ": [{",
                jsonElement("  ", "name", eventName) + ',',
                "  " + asElement("params") + ": {}",
                " }],",
                ' ' + asElement("userProperties") + ": {}",
                "}"
        ).collect(joining(JsonUtil.nl()));
    }

    static String jsonFor(final String eventName,
                          final String clientId,
                          final Map<String, String> eventParameters) {
        requireNonNull(eventName);
        requireNonNull(clientId); 
        requireNonNull(eventParameters);
        return Stream.of(
                "{",
                jsonElement(" ", "clientId", clientId) + ',',
                jsonElement(" ", "userId", clientId) + ',',
                jsonElement(" ", "nonPersonalizedAds", true) + ',',
                ' ' + asElement("events") + ": [{",
                jsonElement("  ", "name", eventName) + ',',
                "  " + asElement("params") + ": {",
                renderMap(eventParameters, e -> jsonElement("   ", e.getKey(), e.getValue())),
                "  }",
                " }]",
                "}"
        ).collect(joining(JsonUtil.nl()));
    }
    static String userProperty(final Map.Entry<String, String> userProperty) {
        return String.format("  %s: {%n %s%n  }", asElement(userProperty.getKey()), jsonElement("   ", "value", userProperty.getValue()));
    }
    
    static String renderMap(final Map<String, String> map, final Function<Map.Entry<String, String>, String> mapper) {
        requireNonNull(map);
        requireNonNull(mapper);
        return map.entrySet().stream()
                .map(mapper)
                .collect(joining(String.format(",%n")));
    }

}
