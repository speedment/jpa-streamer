package com.speedment.jpastreamer.application.standard;


import com.speedment.jpastreamer.application.StreamConfigurationBuilder;
import com.speedment.jpastreamer.application.StreamConfigurationBuilderFactory;
import com.speedment.jpastreamer.application.standard.internal.StandardStreamConfigurationBuilder;

final class StandardStreamConfigurationBuilderFactory implements StreamConfigurationBuilderFactory {

    @Override
    public <T> StreamConfigurationBuilder<T> createStreamConfigurationBuilder(Class<T> entityClass) {
        return new StandardStreamConfigurationBuilder<>(entityClass);
    }
}