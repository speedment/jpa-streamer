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
package com.speedment.jpastreamer.autoclose;

import java.util.stream.Stream;

public interface AutoCloseFactory {

     /**
      * Creates and returns a new wrapped Stream that will call its {@link Stream#close()} method
      * automatically after a terminating operation has been called.
      * <p>
      * N.B. The {@link Stream#iterator()} {@link Stream#spliterator()} methods will throw
      * an {@link UnsupportedOperationException} because otherwise the AutoClose
      * property cannot be guaranteed. This can be unlocked by setting the
      * system property "jpastreamer.allowiteratorandspliterator" to {@code true}.
      *
      * @param <T>  Stream type
      * @author     Per Minborg
      */
     <T> Stream<T> createAutoCloseStream(Stream<T> stream);

}