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
package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;

import static java.util.Objects.requireNonNull;

final class StandardTerminalOperatorModifier implements com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier {

    @Override
    public <T> Pipeline<T> modify(Pipeline<T> pipeline) {
        requireNonNull(pipeline);
        // For now, just return whatever we get.
        return pipeline;
    }
}
