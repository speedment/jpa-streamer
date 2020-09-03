package com.speedment.jpastreamer.streamconfiguration;

public interface StreamConfigurationBuilderFactory {

    <T> StreamConfigurationBuilder<T> createStreamConfigurationBuilder(Class<T> entityClass);

}