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
package com.speedment.jpastreamer.javanine;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 *
 * @author Per Minborg
 */
public interface Java9LongStreamAdditions {

    LongStream takeWhile(LongPredicate predicate);

    LongStream dropWhile(LongPredicate predicate);

}
