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
package com.speedment.jpastreamer.announcer;

/**
 * Providers may provide implementations of this interface to make
 * greeting announcements upon start.
 * <p>
 * The method provided here <em>should not</em> have any side-effects.
 * In particular, this interface is not intended to manage
 * components' life-cycles.
 *
 * @author  Per Minborg
 * @since   0.8.0
 */
public interface Announcer {

    /**
     * Returns a greeting that is to be output upon
     * system start-up.
     * <p>
     * Returning an empty String prevents output.
     *
     * @return a greeting that is to be output upon
     *         system start-up
     */
    String greeting();

}
