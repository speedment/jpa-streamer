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
package com.speedment.jpastreamer.merger;

/**
 * @author Mislav Milicevic
 * @since 0.0.9
 */
public interface MergerFactory {

    /**
     * Creates and returns a {@code CriteriaMerger} instance.
     *
     * @return {@code CriteriaMerger} instance
     */
    CriteriaMerger createCriteriaMerger();

    /**
     * Creates and returns a {@code QueryMerger} instance.
     *
     * @return {@code QueryMerger} instance
     */
    QueryMerger createQueryMerger();
}
