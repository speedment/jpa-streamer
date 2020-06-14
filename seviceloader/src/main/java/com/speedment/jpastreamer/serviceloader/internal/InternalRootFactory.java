package com.speedment.jpastreamer.serviceloader.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;

public final class RootFactory {

    private RootFactory() {}

    public static <S> Optional<S> get(final Class<S> service) {
        return Optional.ofNullable(getHelper(service));
    }

    public static <T> T getOrThrow(final Class<T> service) {
        final T selectedService = getHelper(service);
        if (selectedService == null) {
            throw new NoSuchElementException("Unable to get the service " + service.getName());
        } else {
            return selectedService;
        }
    }

    private static <S> S getHelper(final Class<S> service) {
        final Iterator<S> iterator = ServiceLoader.load(service).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

}