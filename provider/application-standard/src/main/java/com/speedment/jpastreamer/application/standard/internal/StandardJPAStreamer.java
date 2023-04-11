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
package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.analytics.AnalyticsReporterFactory;
import com.speedment.jpastreamer.announcer.Announcer;
import com.speedment.jpastreamer.appinfo.ApplicationInformation;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import jakarta.persistence.EntityManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class StandardJPAStreamer implements JPAStreamer {

    private final Supplier<EntityManager> entityManagerSupplier;
    private final Runnable closeHandler;
    private final Map<StreamConfiguration<?>, Streamer<?>> streamerCache;
    private final AnalyticsReporter analyticsReporter;
    
    private final boolean closeEntityManagers; 
    
    StandardJPAStreamer(final Supplier<EntityManager> entityManagerSupplier, Runnable closeHandler, boolean demoMode, boolean closeEntityManagers) {
        this.closeHandler = requireNonNull(closeHandler);
        this.entityManagerSupplier = requireNonNull(entityManagerSupplier);
        this.closeEntityManagers = closeEntityManagers; 
        streamerCache = new ConcurrentHashMap<>();
        final ApplicationInformation applicationInformation = RootFactory.getOrThrow(ApplicationInformation.class, ServiceLoader::load);
        final AnalyticsReporterFactory analyticsReporterFactory = RootFactory.getOrThrow(AnalyticsReporterFactory.class, ServiceLoader::load);
        analyticsReporter = analyticsReporterFactory.createAnalyticsReporter(applicationInformation.implementationVersion(), demoMode);
        analyticsReporter.start();
        printGreeting(applicationInformation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Stream<T> stream(final StreamConfiguration<T> streamConfiguration) {
        requireNonNull(streamConfiguration);
        if (streamConfiguration.joins().isEmpty()) {
            // Only cache simple configurations to limit the number of objects held
            // See https://github.com/speedment/jpa-streamer/issues/56
            return (Stream<T>) streamerCache
                    .computeIfAbsent(streamConfiguration, ec -> new StandardStreamer<>(streamConfiguration, entityManagerSupplier))
                    .stream();
        } else {
            final Streamer<T> streamer = new StandardStreamer<>(streamConfiguration, entityManagerSupplier);
            return closeEntityManagers ? 
                    streamer.stream().onClose(streamer::close) : 
                    streamer.stream(); 
        }
    }

    @Override
    public void resetStreamer(Class<?>... entityClasses) throws UnsupportedOperationException{
        if (!closeEntityManagers) {
            throw new UnsupportedOperationException("An instance of JPAStreamer.of(Supplier<EntityManager>) is not responsible for the lifecycle of the supplied Entity Managers, and thus cannot reset the Entity Managers."); 
        }
        Arrays.stream(entityClasses)
                .map(StreamConfiguration::of)
                .forEach(streamerCache::remove); 
    }

    @Override
    public void close() {
        streamerCache.values().forEach(Streamer::close);
        analyticsReporter.stop();
        closeHandler.run(); 
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

        // Announce other features
        RootFactory.stream(Announcer.class, ServiceLoader::load)
                .map(Announcer::greeting)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .forEach(System.out::println);

        if (!info.isProductionMode()) {
            System.out.println("This version is NOT INTENDED FOR PRODUCTION USE!");
            System.out.println("View known bugs at https://github.com/speedment/jpa-streamer/issues?q=is%3Aissue+is%3Aopen+label%3Abug");
        }
    }

}
