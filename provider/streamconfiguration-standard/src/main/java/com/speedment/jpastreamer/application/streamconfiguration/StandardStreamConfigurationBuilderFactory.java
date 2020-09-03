package com.speedment.jpastreamer.application.streamconfiguration;

import com.speedment.jpastreamer.application.streamconfiguration.internal.StandardStreamConfigurationBuilder;
import com.speedment.jpastreamer.streamconfiguration.StreamConfigurationBuilder;
import com.speedment.jpastreamer.streamconfiguration.StreamConfigurationBuilderFactory;

import static java.util.Objects.requireNonNull;

final class StandardStreamConfigurationBuilderFactory implements StreamConfigurationBuilderFactory {

    @Override
    public <T> StreamConfigurationBuilder<T> createStreamConfigurationBuilder(final Class<T> entityClass) {
        return new StandardStreamConfigurationBuilder<>(requireNonNull(entityClass));
    }
}