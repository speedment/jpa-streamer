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
