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
import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;

import jakarta.persistence.EntityManager;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class StandardJPAStreamer implements JPAStreamer {

    private final Supplier<EntityManager> entityManagerSupplier;
    private final Runnable closeHandler;
    private final AnalyticsReporter analyticsReporter;
    
    private final boolean closeEntityManagers; 
    
    StandardJPAStreamer(final Supplier<EntityManager> entityManagerSupplier, Runnable closeHandler, boolean demoMode, boolean closeEntityManagers) {
        this.closeHandler = requireNonNull(closeHandler);
        this.entityManagerSupplier = requireNonNull(entityManagerSupplier);
        this.closeEntityManagers = closeEntityManagers; 
        final ApplicationInformation applicationInformation = RootFactory.getOrThrow(ApplicationInformation.class, ServiceLoader::load);
        final AnalyticsReporterFactory analyticsReporterFactory = RootFactory.getOrThrow(AnalyticsReporterFactory.class, ServiceLoader::load);
        analyticsReporter = analyticsReporterFactory.createAnalyticsReporter(applicationInformation.implementationVersion(), demoMode);
        analyticsReporter.start();
        printGreeting(applicationInformation);
    }

    @Override
    public <T> Stream<T> stream(final StreamConfiguration<T> streamConfiguration) {
        requireNonNull(streamConfiguration);
        final StreamSupplier<T> streamer = new StandardStreamSupplier<>(streamConfiguration, entityManagerSupplier, this.closeEntityManagers);
        return closeEntityManagers ? 
                    streamer.stream().onClose(streamer::close) : 
                    streamer.stream();
    }

    @Override
    public <T> StreamSupplier<T> createStreamSupplier(StreamConfiguration<T> streamConfiguration) { 
        requireNonNull(streamConfiguration); 
        return new StandardStreamSupplier<>(streamConfiguration, entityManagerSupplier, this.closeEntityManagers); 
    }

    @Override
    public void resetStreamer(Class<?>... entityClasses) throws UnsupportedOperationException {
        // As there no longer exists a Streamer cache, this method has no effect
    }

    @Override
    public void close() {
        analyticsReporter.stop();
        closeHandler.run(); 
    }

    // Only cache simple configurations to limit the number of objects held
    // See https://github.com/speedment/jpa-streamer/issues/56
    private <T> boolean cached(final StreamConfiguration<T> streamConfiguration) {
        return streamConfiguration.joins().isEmpty() && !streamConfiguration.selections().isPresent(); 
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
