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
 *
 */
package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import static java.util.Objects.requireNonNull;

final class BaseStreamSupport {

    private final Pipeline<?> pipeline;

    public BaseStreamSupport(final Pipeline<?> pipeline) {
        this.pipeline = requireNonNull(pipeline);
    }

    public boolean isParallel() {
        return pipeline.isParallel();
    }

    public void sequential() {
        pipeline.sequential();
    }

    public void parallel() {
        pipeline.parallel();
    }

    public void unordered() {
        pipeline.ordered(false);
    }

    public void onClose(Runnable closeHandler) {
        pipeline.closeHandlers().add(closeHandler);
    }

}
