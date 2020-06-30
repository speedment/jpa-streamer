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