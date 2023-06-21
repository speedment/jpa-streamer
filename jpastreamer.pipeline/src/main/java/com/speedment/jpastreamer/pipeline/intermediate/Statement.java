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
package com.speedment.jpastreamer.pipeline.intermediate;

import static com.speedment.jpastreamer.pipeline.intermediate.Property.*;
import static com.speedment.jpastreamer.pipeline.intermediate.Verb.*;
import static java.util.Objects.requireNonNull;

/**
 * @author Per Minborg
 */
public enum Statement {

    PRESERVES_ORDER(PRESERVES, ORDER),
    PRESERVES_SIZE(PRESERVES, SIZE),
    PRESERVES_TYPE(PRESERVES, TYPE),
    PRESERVES_STREAM_TYPE(PRESERVES, STREAM_TYPE),
    PRESERVES_DISTINCT(PRESERVES, DISTINCT),
    PRESERVES_SORTED(PRESERVES, SORTED),
    PRESERVES_FINITE(PRESERVES, FINITE),
    PRESERVES_SIDE_EFFECT(PRESERVES, SIDE_EFFECT),
    //
    MODIFIES_ORDER(MODIFIES, ORDER),
    MODIFIES_SIZE(MODIFIES, SIZE),
    MODIFIES_TYPE(MODIFIES, TYPE),
    MODIFIES_STREAM_TYPE(MODIFIES, STREAM_TYPE),
    MODIFIES_DISTINCT(MODIFIES, DISTINCT),
    MODIFIES_SORTED(MODIFIES, SORTED),
    MODIFIES_FINITE(MODIFIES, FINITE),
    MODIFIES_SIDE_EFFECT(MODIFIES, SIDE_EFFECT),
    //
    CLEARS_ORDER(CLEARS, ORDER),
    CLEARS_SIZE(CLEARS, SIZE),
    CLEARS_TYPE(CLEARS, TYPE),
    CLEARS_STREAM_TYPE(CLEARS, STREAM_TYPE),
    CLEARS_DISTINCT(CLEARS, DISTINCT),
    CLEARS_SORTED(CLEARS, SORTED),
    CLEARS_FINITE(CLEARS, FINITE),
    CLEARS_SIDE_EFFECT(CLEARS, SIDE_EFFECT);
    //

    Statement(final Verb verb, final Property property) {
        this.verb = requireNonNull(verb);
        this.property = requireNonNull(property);
    }

    private final Verb verb;
    private final Property property;

    public Verb verb() {
        return verb;
    }

    public Property property() {
        return property;
    }
}
