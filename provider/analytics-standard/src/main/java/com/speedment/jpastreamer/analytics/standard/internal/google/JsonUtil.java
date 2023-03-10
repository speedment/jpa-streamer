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

final class JsonUtil {

    private static final String NL = String.format("%n");

    private JsonUtil() {
    }

    static String jsonElement(final String indent,
                              final String key,
                              final Object value) {
        return indent + asElement(key) + ": " + asElement(value);
    }

    static String asElement(final Object value) {
        return value instanceof CharSequence
                ? '"' + escape(value.toString()) + '"'
                : value.toString();

    }

    static String escape(final String raw) {
        return raw
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
        // Todo: escape other non-printing characters ...
    }

    static String nl() {
        return NL;
    }
}
