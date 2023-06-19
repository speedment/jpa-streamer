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
import com.speedment.jpastreamer.analytics.AnalyticsReporterFactory;
import com.speedment.jpastreamer.announcer.Announcer;
import com.speedment.jpastreamer.appinfo.ApplicationInformation;
import com.speedment.jpastreamer.application.JPAStreamerBuilderFactory;
import com.speedment.jpastreamer.application.standard.StandardJPAStreamerBuilderFactory;
import com.speedment.jpastreamer.autoclose.AutoCloseFactory;
import com.speedment.jpastreamer.builder.BuilderFactory;
import com.speedment.jpastreamer.renderer.RendererFactory;
import com.speedment.jpastreamer.streamconfiguration.StreamConfigurationFactory;

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
module jpastreamer.application.standard {
    requires transitive jpastreamer.application;
    requires transitive jpastreamer.streamconfiguration;
    requires jpastreamer.analytics;
    requires jpastreamer.appinfo;
    requires jpastreamer.renderer;
    requires jpastreamer.builder;
    requires jpastreamer.autoclose;
    requires jpastreamer.announcer;
    requires jpastreamer.rootfactory;

    exports com.speedment.jpastreamer.application.standard;
    
    uses Announcer;
    uses ApplicationInformation;
    uses AnalyticsReporterFactory;
    uses RendererFactory; 
    uses BuilderFactory; 
    uses AutoCloseFactory;
    uses StreamConfigurationFactory;
    
    provides JPAStreamerBuilderFactory with StandardJPAStreamerBuilderFactory;
}
