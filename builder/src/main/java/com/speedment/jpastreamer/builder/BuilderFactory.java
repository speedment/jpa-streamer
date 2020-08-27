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
package com.speedment.jpastreamer.builder;

import com.speedment.jpastreamer.renderer.Renderer;

import java.util.stream.Stream;

public interface BuilderFactory {

     /**
      * Creates and returns a new Stream that is parsable and
      * can optimize its pipeline and merge internal operations
      * into the stream source (e.g. SQL or a JPA Query).
      *
      * @param <T>  Stream type
      * @param root class to use as stream source
      * @param renderer to use
      * @author     Per Minborg
      */
     <T> Stream<T> createBuilder(Class<T> root, Renderer renderer);

}