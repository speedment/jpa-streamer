package com.speedment.jpastreamer.serviceloader;

import com.speedment.jpastreamer.serviceloader.internal.InternalServiceLoaderUtil;

import java.util.Optional;

public final class ServiceLoaderUtil {
    private ServiceLoaderUtil() {}

    public static <T> Optional<T> get(final Class<T> classToken) {
        return InternalServiceLoaderUtil.get(classToken);
    }

    public static <T> T getOrThrow(final Class<T> classToken) {
        return InternalServiceLoaderUtil.getOrThrow(classToken);
    }

}