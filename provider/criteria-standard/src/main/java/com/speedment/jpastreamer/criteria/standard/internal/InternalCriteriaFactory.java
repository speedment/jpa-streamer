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

package com.speedment.jpastreamer.criteria.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.CriteriaFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class InternalCriteriaFactory implements CriteriaFactory {

    @Override
    public <T> Criteria<T> createCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<T> query,
        final Root<T> root
    ) {
        requireNonNull(builder);
        requireNonNull(query);
        requireNonNull(root);

        return new InternalCriteria<>(builder, query, root);
    }
}
