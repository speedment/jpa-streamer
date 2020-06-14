package com.speedment.jpastreamer.rootfactory.internal;

import java.util.*;

public final class InternalRootFactory {

    private InternalRootFactory() {
    }

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
            fail(service, "Provider " + standardServiceName + " not found");
        }
        if (!service.isAssignableFrom(clazz)) {
            fail(service, "Provider " + standardServiceName + " not a subtype");
        }
        try {
            final Object instance = clazz.newInstance();
            final S p = service.cast(instance);
            System.out.println("Warning: " + service + " implementation guessed to be " + instance.getClass().getName() + ". " +
                    "This should be fixed to ensure performance and stability.");
            return p;
        } catch (Throwable x) {
            fail(service, "Provider " + standardServiceName + " could not be instantiated", x);
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