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
package com.speedment.jpastreamer.fieldgenerator;

import com.google.auto.service.AutoService;
import com.speedment.jpastreamer.fieldgenerator.internal.InternalFieldGeneratorProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * JPAStreamer standard annotation processor that generates fields for classes annotated
 * with {@code Entity}.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

@SupportedAnnotationTypes("javax.persistence.Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public final class StandardFieldGeneratorProcessor extends AbstractProcessor {

    private final AbstractProcessor delegate = new InternalFieldGeneratorProcessor();

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        delegate.init(env);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return delegate.process(annotations, roundEnv);
    }

}