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
module jpastreamer.field {
    requires com.speedment.common.invariant;
    requires transitive java.persistence; // We expose this in fields via AttributeConverter

    requires transitive com.speedment.common.function;
    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.runtime.compute;

    exports com.speedment.jpastreamer.field;
    exports com.speedment.jpastreamer.field.collector;
    exports com.speedment.jpastreamer.field.comparator;
    exports com.speedment.jpastreamer.field.exception;
    exports com.speedment.jpastreamer.field.expression;
    exports com.speedment.jpastreamer.field.method;
    exports com.speedment.jpastreamer.field.predicate;
    exports com.speedment.jpastreamer.field.predicate.trait;
    exports com.speedment.jpastreamer.field.trait;
}
