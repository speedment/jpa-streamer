package com.speedment.jpastreamer.autoclose.standard;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.autoclose.standard.internal.InternalAutoCloseFactory;

import java.util.stream.Stream;

public final class StandardAutoCloseFactory implements AutoCloseFactory {

    private final AutoCloseFactory delegate;

    public StandardAutoCloseFactory() {
        this.delegate = new InternalAutoCloseFactory();
    }

    @Override
    public <T> Stream<T> createAutoCloseStream(final Stream<T> stream) {
        return delegate.createAutoCloseStream(stream);
    }
}