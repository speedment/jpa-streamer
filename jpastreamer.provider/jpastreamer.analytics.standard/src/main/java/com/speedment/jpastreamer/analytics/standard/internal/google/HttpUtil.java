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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static java.util.Objects.requireNonNull;

final class HttpUtil {

    private static final int DEFAULT_TIME_OUT_MS = 2_000;

    private static final String THREAD_NAME = "chronicle~analytics~http~client";
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        final Thread thread = new Thread(runnable, THREAD_NAME);
        thread.setDaemon(true);
        return thread;
    });

    private HttpUtil() {
    }

    public static void send(final String urlString,
                            final String body) {
        requireNonNull(urlString); 
        requireNonNull(body);
        EXECUTOR.execute(new Sender(urlString, body));
    }

    static String urlEncode(final String s) {
        requireNonNull(s); 
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Exception while URL encoding statistics for Google Analytics.");
            e.printStackTrace();
            throw new InternalAnalyticsException("This should never happen as " + StandardCharsets.UTF_8 + " should always be present.");
        }
    }

    static final class Sender implements Runnable {

        private final String urlString;
        private final String body;

        Sender(final String urlString,
               final String body) {
            requireNonNull(urlString); 
            requireNonNull(body); 
            this.urlString = urlString;
            this.body = body;
        }

        @Override
        public void run() {
            try {
                final URL url = new URL(urlString);
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // Do not linger if the connection is slow. Give up instead!
                conn.setConnectTimeout(DEFAULT_TIME_OUT_MS);
                conn.setReadTimeout(DEFAULT_TIME_OUT_MS);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
                conn.setRequestProperty("t", "application/json");
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    final byte[] output = body.getBytes(StandardCharsets.UTF_8);
                    os.write(output, 0, output.length);
                    os.flush();
                }

                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    final StringBuilder response = new StringBuilder();
                    String sep = "";
                    for (String responseLine; (responseLine = br.readLine()) != null; ) {
                        response.append(sep).append(responseLine); // preserve some white space
                        sep = " ";
                    }
                    final String logMsg = response.toString().replaceAll("\\s+(?=\\S)", " ");
                    if (!logMsg.isEmpty())
                        System.out.println(logMsg);
                }

            } catch (IOException ioe) {
                System.err.println("Exception while sending usage statistics to Google Analytics.");
                ioe.printStackTrace(); 
            }
        }
    }
}
