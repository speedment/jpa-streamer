/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
module jpastreamer.field {
    requires com.speedment.common.invariant;
    requires transitive java.persistence; // We expose this in fields via AttributeConverter

    requires transitive com.speedment.common.function;
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
