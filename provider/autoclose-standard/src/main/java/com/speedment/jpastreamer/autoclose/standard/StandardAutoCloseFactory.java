package com.speedment.jpastreamer.autoclose.standard;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class StandardAutoCloseFactory implements AutoCloseFactory {

    @Override
    public <T> Stream<T> createAutoCloseStream(final Supplier<Stream<T>> streamSupplier) {
        requireNonNull(streamSupplier);
        return null;
    }
}
