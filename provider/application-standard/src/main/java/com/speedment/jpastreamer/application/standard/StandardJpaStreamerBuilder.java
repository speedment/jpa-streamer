package com.speedment.jpastreamer.application.standard;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;
import com.speedment.jpastreamer.application.standard.internal.InternalJpaStreamerBuilder;

public final class StandardJpaStreamerBuilder implements JpaStreamerBuilder {

    private final JpaStreamerBuilder delegate = new InternalJpaStreamerBuilder();

    @Override
    public JpaStreamerBuilder withPersistenceUnitName(String persistenceUnitName) {
        return delegate.withPersistenceUnitName(persistenceUnitName);
    }

    @Override
    public JpaStreamer build() {
        return delegate.build();
    }

}