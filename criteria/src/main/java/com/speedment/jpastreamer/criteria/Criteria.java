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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Mislav Miliceivc
 * @since 0.0.9
 */
public interface Criteria<T> {

    /**
     * Returns the {@code CriteriaBuilder} that is stored within this {@code Criteria}
     *
     * @return the {@code CriteriaBuilder} that is stored within this {@code Criteria}
     */
    CriteriaBuilder getBuilder();

    /**
     * Returns the {@code CriteriaQuery} that is stored within this {@code Criteria}
     *
     * @return the {@code CriteriaQuery} that is stored within this {@code Criteria}
     */
    CriteriaQuery<T> getQuery();

    /**
     * Returns the {@code Root} that is stored within this {@code Criteria}
     *
     * @return the {@code Root} that is stored within this {@code Criteria}
     */
    Root<T> getRoot();
}
