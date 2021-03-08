/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.rootfactory.internal;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

public final class InternalRootFactory {

    public static final String PROVIDER = "Provider ";

    private InternalRootFactory() {
    }

    public static <S> Optional<S> get(final Class<S> service, final Function<Class<S>, ServiceLoader<S>> loader) {
        requireNonNull(service);
        requireNonNull(loader);
        return Optional.ofNullable(getHelper(service, loader));
    }

    public static <S> S getOrThrow(final Class<S> service, Function<Class<S>, ServiceLoader<S>> loader) {
        requireNonNull(service);
        requireNonNull(loader);
        final S selectedService = getHelper(service, loader);
        if (selectedService == null) {
            throw new NoSuchElementException("Unable to get the service " + service.getName());
        } else {
            return selectedService;
        }
    }

    public static <S> Stream<S> stream(final Class<S> service, final Function<Class<S>, ServiceLoader<S>> loader ) {
        requireNonNull(service);
        requireNonNull(loader);
        final Iterator<S> iterator = loader.apply(service).iterator();
        if (iterator.hasNext()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL), false);
        } else {
            // If not found though the ServiceLoader, try to guess the standard implementation
            try {
                final S s = getStandard(service);
                return Stream.of(s);
            } catch (ServiceConfigurationError | NoSuchElementException e) {
                return Stream.empty();
            }
        }

    }

    private static <S> S getHelper(final Class<S> service,  Function<Class<S>, ServiceLoader<S>> loader) {
        final Iterator<S> iterator = loader.apply(service).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            // If not found though the ServiceLoader, try to guess the standard implementation
            return getStandard(service);
        }
    }

    private static <S> S getStandard(final Class<S> service) {
        final String standardServiceName = service.getPackage().getName() + ".standard." + "Standard" + service.getSimpleName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(standardServiceName);
        } catch (ClassNotFoundException x) {
            fail(service, PROVIDER + standardServiceName + " not found");
        }
        if (!service.isAssignableFrom(clazz)) {
            fail(service, PROVIDER + standardServiceName + " not a subtype");
        }
        try {
            final Object instance = clazz.getDeclaredConstructor().newInstance();
            final S p = service.cast(instance);
            System.out.println("Warning: " + service + " implementation guessed to be " + instance.getClass().getName() + ". " +
                    "This should be fixed to ensure performance and stability.");
            return p;
        } catch (Exception x) {
            fail(service, PROVIDER + standardServiceName + " could not be instantiated", x);
        }
        throw new NoSuchElementException("Error"); // we should never end up here
    }

    private static void fail(Class<?> service, String msg, Throwable cause) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg, cause);
    }

    private static void fail(Class<?> service, String msg) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

}
