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
package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.analytics.AnalyticsReporterFactory;
import com.speedment.jpastreamer.appinfo.ApplicationInformation;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class StandardJPAStreamer implements JPAStreamer {

    private final EntityManagerFactory entityManagerFactory;
    private final boolean closeEntityManager;
    private final Map<StreamConfiguration<?>, Streamer<?>> streamerCache;
    private final AnalyticsReporter analyticsReporter;

    StandardJPAStreamer(final EntityManagerFactory entityManagerFactory, final boolean closeEntityManager) {
        this.closeEntityManager = closeEntityManager;
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
        streamerCache = new ConcurrentHashMap<>();
        final ApplicationInformation applicationInformation = RootFactory.getOrThrow(ApplicationInformation.class, ServiceLoader::load);
        final AnalyticsReporterFactory analyticsReporterFactory = RootFactory.getOrThrow(AnalyticsReporterFactory.class, ServiceLoader::load);

        final boolean demoMode = "sakila".equals(this.entityManagerFactory.getProperties().getOrDefault("hibernate.ejb.persistenceUnitName", ""));

        analyticsReporter = analyticsReporterFactory.createAnalyticsReporter(applicationInformation.implementationVersion(), demoMode);
        analyticsReporter.start();
        printGreeting(applicationInformation);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> Stream<T> stream(StreamConfiguration<T> streamConfiguration) {
        return (Stream<T>) streamerCache
                .computeIfAbsent(streamConfiguration, ec -> new StandardStreamer<>(streamConfiguration, entityManagerFactory))
                .stream();
    }

    @Override
    public void close() {
        streamerCache.values().forEach(Streamer::close);
        analyticsReporter.stop();
        if (closeEntityManager) {
            entityManagerFactory.close();
        }
    }

    private void printGreeting(final ApplicationInformation info) {
        final String greeting = String.format("%s%n" +
                        ":: %s %s :: %s%n" +
                        "Copyright %s%n" +
                        "Licensed under %s%n" +
                        "Running under %s %s",
                info.banner(),
                info.title(), info.subtitle(), info.implementationVersion(),
                info.vendor(),
                info.licenseName(),
                System.getProperty("java.runtime.name"), System.getProperty("java.runtime.version")
        );
        System.out.println(greeting);
        if (!info.isProductionMode()) {
            System.out.println("This version is NOT INTENDED FOR PRODUCTION USE!");
        }
    }

}
