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

import com.speedment.jpastreamer.criteria.Criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class InternalCriteria<ENTITY, RETURN> implements Criteria<ENTITY, RETURN> {

    private final CriteriaBuilder builder;
    private final CriteriaQuery<RETURN> query;
    private final Root<ENTITY> root;

    public InternalCriteria(
        final CriteriaBuilder builder,
        final CriteriaQuery<RETURN> query,
        final Root<ENTITY> root
    ) {
        this.builder = builder;
        this.query = query;
        this.root = root;
    }

    @Override
    public CriteriaBuilder getBuilder() {
        return builder;
    }

    @Override
    public CriteriaQuery<RETURN> getQuery() {
        return query;
    }

    @Override
    public Root<ENTITY> getRoot() {
        return root;
    }
}
