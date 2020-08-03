/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.speedment.jpastreamer.integration.spring.autoconfigure;

import com.speedment.jpastreamer.application.JPAStreamer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * A Spring Boot autoconfiguration class that adds support for JPAStreamer autowiring.
 *
 * @author Mislav Milicevic
 * @since 0.0.9
 */
@Configuration
@ConditionalOnClass({JPAStreamer.class, EntityManagerFactory.class})
class JPAStreamerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    JPAStreamer jpaStreamer(EntityManagerFactory entityManagerFactory) {
        return JPAStreamer.createJPAStreamerBuilder(entityManagerFactory).build();
    }
}
