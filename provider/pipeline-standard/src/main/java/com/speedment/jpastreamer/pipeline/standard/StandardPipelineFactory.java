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
package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.PipelineFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.pipeline.InternalPipelineFactory;

public final class StandardPipelineFactory implements PipelineFactory {

    private final PipelineFactory delegate;

    public StandardPipelineFactory() {
        this.delegate = new InternalPipelineFactory();
    }

    @Override
    public <T> Pipeline<T> createPipeline(final Class<T> rootClass) {
        return delegate.createPipeline(rootClass);
    }

}