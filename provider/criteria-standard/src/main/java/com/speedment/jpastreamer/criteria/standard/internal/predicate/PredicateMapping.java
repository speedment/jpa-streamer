/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.criteria.standard.internal.predicate;

import com.speedment.jpastreamer.criteria.QueryParameter;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PredicateMapping {

    private final Predicate predicate;
    private final List<QueryParameter<?>> queryParameters = new ArrayList<>();

    public PredicateMapping(Predicate predicate, QueryParameter<?>... queryParameters) {
        this.predicate = predicate;
        this.queryParameters.addAll(Arrays.asList(queryParameters));
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public List<QueryParameter<?>> getQueryParameters() {
        return Collections.unmodifiableList(queryParameters);
    }
}
