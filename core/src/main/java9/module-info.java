/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
module jpastreamer.core {
    exports com.speedment.jpastreamer.core;

    requires transitive java.persistence;
    requires transitive jpastreamer.application;
    requires transitive jpastreamer.field;

    // These requires are needed to make JavaDoc work
    requires jpastreamer.application.standard;
    requires jpastreamer.autoclose.standard;
    requires jpastreamer.pipeline.standard;
    requires jpastreamer.builder.standard;
    requires jpastreamer.renderer.standard;
    requires jpastreamer.interopoptimizer.standard;
    requires jpastreamer.merger.standard;
    requires jpastreamer.criteria.standard;
    requires jpastreamer.analytics.standard;
    requires jpastreamer.appinfo.standard;
    requires jpastreamer.fieldgenerator.standard;
    requires jpastreamer.streamconfiguration.standard;

}