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

package com.speedment.jpastreamer.criteria;

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

import javax.persistence.criteria.Predicate;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface PredicateFactory {

    /**
     * Creates and returns a JPA {@code Predicate} with the {@code SpeedmentPredicate} serving
     * as the model. The JPA {@code Predicate} is created using the provided {@code criteria}
     *
     * @param criteria used to create the JPA Predicate
     * @param speedmentPredicate used as a model for the JPA Predicate that is being created
     * @param <ENTITY> root entity used in the Speedment Predicate
     * @return JPA Predicate
     */
    <ENTITY> Predicate createPredicate(
        final Criteria<ENTITY, ?> criteria,
        final SpeedmentPredicate<ENTITY> speedmentPredicate
    );
}
