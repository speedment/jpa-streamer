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
package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.autoclose.AutoCloseFactory;

import java.util.stream.Stream;

public final class InternalAutoCloseFactory implements AutoCloseFactory {

    @Override
    public <T> Stream<T> createAutoCloseStream(Stream<T> stream) {
        return new AutoClosingStream<>(stream);
    }
}