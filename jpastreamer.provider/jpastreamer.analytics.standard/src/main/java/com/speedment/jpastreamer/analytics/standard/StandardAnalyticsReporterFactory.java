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
package com.speedment.jpastreamer.analytics.standard;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.analytics.AnalyticsReporterFactory;
import com.speedment.jpastreamer.analytics.standard.internal.google.GoogleAnalyticsHandler;
import com.speedment.jpastreamer.analytics.standard.internal.InternalStandardAnalyticsReporter;

public final class StandardAnalyticsReporterFactory implements AnalyticsReporterFactory {
    
    @Override
    public AnalyticsReporter createAnalyticsReporter(final String version, final boolean demoMode) {
        return new InternalStandardAnalyticsReporter(new GoogleAnalyticsHandler(version, demoMode));
    }
}
