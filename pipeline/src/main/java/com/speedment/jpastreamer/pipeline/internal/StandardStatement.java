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
package com.speedment.jpastreamer.pipeline.internal;

import com.speedment.jpastreamer.pipeline.action.Property;
import com.speedment.jpastreamer.pipeline.action.Statement;
import com.speedment.jpastreamer.pipeline.action.Verb;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Per Minborg
 */
public final class StandardStatement implements Statement {

    private final Verb verb;
    private final Property property;
    private static final Map<Verb, Map<Property, StandardStatement>> SINGLETONS = new ConcurrentHashMap<>();

    private StandardStatement(Verb verb, Property property) {
        this.verb = requireNonNull(verb);
        this.property = requireNonNull(property);
    }

    @Override
    public String toString() {
        return getVerb() + " " + getProperty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVerb(), getProperty());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StandardStatement other = (StandardStatement) obj;
        if (this.getVerb() != other.getVerb()) {
            return false;
        }
        return this.getProperty() == other.getProperty();
    }

    @Override
    public Verb getVerb() {
        return verb;
    }

    @Override
    public Property getProperty() {
        return property;
    }

    public static StandardStatement of(final Verb verb, final Property property) {
        requireNonNull(verb);
        requireNonNull(property);
        return SINGLETONS
                .computeIfAbsent(verb, unused -> new ConcurrentHashMap<>())
                .computeIfAbsent(property, unused -> new StandardStatement(verb, property));
    }

}