package com.speedment.jpastreamer.application;

public interface StreamConfigurationBuilderFactory {

    <T> StreamConfigurationBuilder<T> createStreamConfigurationBuilder(Class<T> entityClass);

}