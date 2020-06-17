package com.speedment.jpastreamer.fieldgenerator.standard;

import com.google.auto.service.AutoService;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.persistence.Entity;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.util.Formatting.shortName;

/**
 * JPAStreamer standard annotation processor that generates fields for classes annotated
 * with {@code Entity}.
 *
 * @author Julia Gustafsson
 */

@SupportedAnnotationTypes("javax.persistence.Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public final class StandardFieldGeneratorProcessor extends AbstractProcessor {

    private ProcessingEnvironment processingEnvironment;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        this.processingEnvironment = env;
        this.elementUtils = processingEnvironment.getElementUtils();

        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "JPA Streamer Field Generator Processor");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if(annotations.size() == 0 || roundEnv.processingOver()) {
            // Allow other processors to run
            return false;
        }

        roundEnv.getElementsAnnotatedWith(Entity.class).stream()
                .filter(ae -> ae.getKind() == ElementKind.CLASS)
                .forEach(ae -> {
                    try {
                        generateFields(ae);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });

        return true;
    }

    private void generateFields(Element annotatedElement) throws IOException {

        // Retrieve all declared fields of the annotated class
        Set<? extends Element> enclosedFields = annotatedElement.getEnclosedElements().stream()
                .filter(ee -> ee.getKind().isField()
                        && !ee.getModifiers().contains(Modifier.FINAL)) // Ignore immutable fields
                .collect(Collectors.toSet());

        String annotatedClassName = shortName(annotatedElement.asType().toString());
        String fieldClassName = annotatedClassName + "$";

        PackageElement packageElement = processingEnvironment.getElementUtils().getPackageOf(annotatedElement);
        String packageName;
        if (packageElement.isUnnamed()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Class " + annotatedClassName + "has an unnamed package.");
            packageName = "";
        } else {
            packageName = packageElement.getQualifiedName().toString();
        }

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fieldClassName);
        Writer writer = builderFile.openWriter();

        File file = getFileContent(enclosedFields, annotatedClassName, fieldClassName, packageName);
        writer.write(new JavaGenerator().on(file).get());
        writer.close();
    }

    private File getFileContent(Set<? extends Element> enclosedFields, String annotatedClassName, String genClassName, String packageName) {

        File file = packageName.isEmpty() ?
                File.of(genClassName + ".java") :
                File.of(packageName + "/" + genClassName + ".java");
        Class clazz = Class.of(genClassName).public_();

        enclosedFields
                .forEach(field -> {
                    addFieldToClass(field, clazz);
                });

        file.add(clazz);
        return file;
    }

    private void addFieldToClass(Element field, Class clazz) {
        SimpleType fieldType = SimpleType.create(field.asType().toString());

        clazz.add(Field.of(field.getSimpleName().toString(), fieldType)
                .public_()
                .static_()
                .final_());
    }

}