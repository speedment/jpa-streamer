package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class InternalAutoCloseFactory implements AutoCloseFactory {

    @Override
    public <T> Stream<T> createAutoCloseStream(Supplier<Stream<T>> streamSupplier) {
        return null;
    }
}