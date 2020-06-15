/*
 *
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