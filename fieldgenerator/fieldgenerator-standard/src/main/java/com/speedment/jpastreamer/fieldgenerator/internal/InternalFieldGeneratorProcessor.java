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
package com.speedment.jpastreamer.fieldgenerator.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.controller.AlignTabs;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.jpastreamer.field.exception.IllegalJavaBeanException;
import com.speedment.jpastreamer.fieldgenerator.exception.FieldGeneratorProcessorException;
import com.speedment.jpastreamer.fieldgenerator.internal.typeparser.TypeParser;
import com.speedment.jpastreamer.field.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * JPAStreamer standard annotation processor that generates fields for classes annotated
 * with {@code Entity}.
 *
 * @author Julia Gustafsson
 * @since 0.1.0
 */

public final class InternalFieldGeneratorProcessor extends AbstractProcessor {

    protected static final String GET_PREFIX = "get";
    protected static final String IS_PREFIX = "is";

    private static final Generator generator = Generator.forJava();

    private ProcessingEnvironment processingEnvironment;
    private Elements elementUtils;
    private Types typeUtils;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        this.processingEnvironment = env;
        this.elementUtils = processingEnvironment.getElementUtils();
        this.typeUtils = processingEnvironment.getTypeUtils();

        messager = processingEnvironment.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "JPA Streamer Field Generator Processor");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.size() == 0 || roundEnv.processingOver()) {
            // Allow other processors to run
            return false;
        }

        roundEnv.getElementsAnnotatedWith(Entity.class).stream()
                .filter(ae -> ae.getKind() == ElementKind.CLASS)
                .forEach(ae -> {
                    try {
                        String qualifiedGenEntityName = ae.asType().toString() + "$";
                        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(qualifiedGenEntityName);
                        Writer writer = builderFile.openWriter();
                        generateFields(ae, writer);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return true;
    }

    void generateFields(final Element annotatedElement, final Writer writer) throws IOException {

        final String entityName = shortName(annotatedElement.asType().toString());
        final String genEntityName = entityName + "$";

        final Map<String, Element> getters = annotatedElement.getEnclosedElements().stream()
                .filter(ee -> ee.getKind() == ElementKind.METHOD)
                // Only consider methods with no parameters
                .filter(ee -> ee.getEnclosedElements().stream().noneMatch(eee -> eee.getKind() == ElementKind.PARAMETER))
                // Todo: Filter out methods that returns void or Void
                .collect(toMap(e -> e.getSimpleName().toString(), Function.identity()));

        final Set<String> isGetters = getters.values().stream()
                // todo: Filter out methods only returning boolean or Boolean
                .map(Element::getSimpleName)
                .map(Object::toString)
                .filter(n -> n.startsWith(IS_PREFIX))
                .map(n -> n.substring(2))
                .map(Formatting::lcfirst)
                .collect(toSet());

        // Retrieve all declared non-final instance fields of the annotated class
        Map<? extends Element, String> enclosedFields = annotatedElement.getEnclosedElements().stream()
                .filter(ee -> ee.getKind().isField()
                        && !ee.getModifiers().contains(Modifier.STATIC) // Ignore static fields
                        && !ee.getModifiers().contains(Modifier.FINAL)) // Ignore final fields
                .collect(
                        toMap(
                                Function.identity(),
                                ee -> findGetter(ee, getters, isGetters, entityName, lombokGetterAvailable(annotatedElement, ee)))
                );

/*        if (annotatedElement.getSimpleName().toString().contains("User")) {
            messager.printMessage(Diagnostic.Kind.NOTE, " "+ isGetters.size());
            messager.printMessage(Diagnostic.Kind.NOTE, " "+ isGetters.iterator().next());
            messager.printMessage(Diagnostic.Kind.NOTE, enclosedFields.toString());
            throw new UnsupportedEncodingException(isGetters.toString());
        }*/


        //messager.printMessage(Diagnostic.Kind.NOTE, annotatedElement.getSimpleName().toString() + " " +isGetters.size());


        final PackageElement packageElement = processingEnvironment.getElementUtils().getPackageOf(annotatedElement);
        String packageName;
        if (packageElement.isUnnamed()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Class " + entityName + " has an unnamed package.");
            packageName = "";
        } else {
            packageName = packageElement.getQualifiedName().toString();
        }

        final File file = generatedEntity(enclosedFields, entityName, genEntityName, packageName);
        writer.write(generator.on(file).get());
    }

    private String findGetter(final Element field,
                              final Map<String, Element> getters,
                              final Set<String> isGetters,
                              final String entityName,
                              boolean lombokGetterAvailable) {
        
        final String fieldName = field.getSimpleName().toString();
        final String getterPrefix = isGetters.contains(fieldName)
                ? IS_PREFIX
                : GET_PREFIX;

        final String standardJavaName = javaNameFromExternal(fieldName);

        final String standardGetterName = getterPrefix + standardJavaName;

        final Element standardGetter = getters.get(standardGetterName);

        if (standardGetter != null || lombokGetterAvailable) {
            // We got lucky because the user elected to conform
            // to the standard JavaBean notation.
            return entityName + "::" + standardGetterName;
        }

        final String lambdaName = lcfirst(entityName);

        if (!field.getModifiers().contains(Modifier.PROTECTED) && !field.getModifiers().contains(Modifier.PRIVATE)) {
            // We can use a lambda. Great escape hatch!
            return lambdaName + " -> " + lambdaName + "." + fieldName;
        }

        // default to thrower

        // Todo: This should be an error in the future
        messager.printMessage(Diagnostic.Kind.WARNING, "Class " + entityName + " is not a proper JavaBean because " + field.getSimpleName().toString() + " has no standard getter. Fix the issue to ensure stability.");
        return lambdaName + " -> {throw new " + IllegalJavaBeanException.class.getSimpleName() + "(" + entityName + ".class, \"" + fieldName + "\");}";

    }

    private File generatedEntity(Map<? extends Element, String> enclosedFields, String entityName, String genEntityName, String packageName) {
        final File file = packageName.isEmpty() ?
                File.of(genEntityName + ".java") :
                File.of(packageName + "/" + genEntityName + ".java");

        final Class clazz = Class.of(genEntityName)
                .public_()
                .final_()
                .set(Javadoc.of(
                        "The generated base for entity {@link " + entityName + "} representing entities of the"
                                + " {@code " + lcfirst(entityName) + "}-table in the database." +
                                nl() + "<p> This file has been automatically generated by JPAStreamer."
                ).author("JPAStreamer"));

        enclosedFields
                .forEach((field, getter) -> {
                    addFieldToClass(field, getter, clazz, entityName);
                    // Name magic...
                    if (getter.contains(IllegalJavaBeanException.class.getSimpleName())) {
                        file.add(Import.of(IllegalJavaBeanException.class));
                    }
                });

        file.add(clazz);
        file.call(new AutoImports(generator.getDependencyMgr())).call(new AlignTabs<>());
        return file;
    }

    private void addFieldToClass(final Element field,
                                 final String getter,
                                 final Class clazz,
                                 final String entityName) {
        final String fieldName = field.getSimpleName().toString();
        final Type referenceType = referenceType(field, entityName);

        // Begin building the field value parameters
        final List<Value<?>> fieldParams = new ArrayList<>();

        // Add table entity
        fieldParams.add(Value.ofReference(entityName + ".class"));

        // Add the attribute name
        fieldParams.add(Value.ofText(fieldName));

        // Add getter method reference
        fieldParams.add(Value.ofReference(getter));

        final TypeElement typeElement = elementUtils.getTypeElement(fieldType(field).getTypeName());
        final TypeMirror enumType = elementUtils.getTypeElement("java.lang.Enum").asType();

        if (typeElement != null && typeUtils.isAssignable(typeElement.asType(), enumType)) {
            final String fieldTypeName = shortName(fieldType(field).getTypeName());

            // Add enum class
            fieldParams.add(Value.ofReference(fieldTypeName + ".class"));
        } else {
            // Add the 'unique' boolean to the end for all field but enum
            final Column col = field.getAnnotation(Column.class);
            fieldParams.add(Value.ofBoolean(col != null && col.unique()));
        }

        clazz.add(Field.of(fieldName, referenceType)
                .public_().static_().final_()
                .set(Value.ofInvocation(
                        referenceType,
                        "create",
                        fieldParams.toArray(new Value<?>[0])
                ))
                .set(Javadoc.of(
                        "This Field corresponds to the {@link " + entityName + "} field " + fieldName + "."
                )));
    }

    private Type referenceType(Element field, String entityName) throws FieldGeneratorProcessorException {
        Type fieldType = fieldType(field);
        Type entityType = SimpleType.create(entityName);
        final Type type;

        TypeElement typeElement = elementUtils.getTypeElement(fieldType(field).getTypeName());
        TypeMirror enumType = elementUtils.getTypeElement("java.lang.Enum").asType();
        TypeMirror comparableType = typeUtils.erasure(elementUtils.getTypeElement("java.lang.Comparable").asType());

        try {
            if (field.asType().getKind().isPrimitive()) {
                type = primitiveFieldType(fieldType, entityType);
            } else if (typeElement != null && typeUtils.isAssignable(typeElement.asType(), enumType)) {
                type = SimpleParameterizedType.create(
                        EnumField.class,
                        entityType,
                        fieldType);
            } else if (typeElement != null && typeUtils.isAssignable(typeElement.asType(), comparableType) && field.getAnnotation(Lob.class) == null) {
                type = fieldType(field).getTypeName().contains("String") ?
                        SimpleParameterizedType.create(
                                StringField.class,
                                entityType) :
                        SimpleParameterizedType.create(
                                ComparableField.class,
                                entityType,
                                fieldType);
            } else {
                type = SimpleParameterizedType.create(
                        ReferenceField.class,
                        entityType,
                        fieldType);
            }
        } catch (UnsupportedOperationException e) {
            throw new FieldGeneratorProcessorException("Primitive type " + fieldType.getTypeName() + " could not be parsed.");
        }

        return type;
    }

    private Type fieldType(Element field) {
        TypeParser typeParser = new TypeParser();
        return typeParser.render(field.asType().toString());
    }

    private Type primitiveFieldType(Type fieldType, Type entityType) throws UnsupportedOperationException {
        Type primitiveFieldType;
        switch (fieldType.getTypeName()) {
            case "int":
                primitiveFieldType = IntField.class;
                break;
            case "byte":
                primitiveFieldType = ByteField.class;
                break;
            case "short":
                primitiveFieldType = ShortField.class;
                break;
            case "long":
                primitiveFieldType = LongField.class;
                break;
            case "float":
                primitiveFieldType = FloatField.class;
                break;
            case "double":
                primitiveFieldType = DoubleField.class;
                break;
            case "char":
                primitiveFieldType = CharField.class;
                break;
            case "boolean":
                primitiveFieldType = BooleanField.class;
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unknown primitive type: '" + fieldType.getTypeName() + "'."
                );
        }
        return SimpleParameterizedType.create(
                primitiveFieldType,
                entityType
        );
    }

    private static final Map<String, java.lang.Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    private static final Set<String> DISALLOWED_ACCESS_LEVELS = Stream.of("PROTECTED", "PRIVATE", "NONE")
            .collect(Collectors.collectingAndThen(toSet(), Collections::unmodifiableSet));

    private boolean lombokGetterAvailable(Element classElement, Element fieldElement) {
        final boolean globalEnable = isLombokAnnotated(classElement, "Data") || isLombokAnnotated(classElement, "Getter");
        final boolean localEnable = isLombokAnnotated(fieldElement, "Getter");
        final boolean disallowedAccessLevel = DISALLOWED_ACCESS_LEVELS.contains(getterAccessLevel(fieldElement).orElse("No access level defined"));
        return !disallowedAccessLevel && (globalEnable || localEnable);
    }

    private boolean isLombokAnnotated(final Element annotatedElement, final String lombokSimpleClassName) {
        try {
            final String className = "lombok." + lombokSimpleClassName;
            final java.lang.Class<java.lang.annotation.Annotation> clazz = (java.lang.Class<java.lang.annotation.Annotation>) java.lang.Class.forName(className);
            return annotatedElement.getAnnotation(clazz) != null;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    private Optional<String> getterAccessLevel(final Element fieldElement) {

        final List<? extends AnnotationMirror> mirrors = fieldElement.getAnnotationMirrors();

        Map<? extends ExecutableElement, ? extends AnnotationValue> map = mirrors.stream()
                .filter(am -> "lombok.Getter".equals(am.getAnnotationType().toString()))
                .findFirst()
                .map(AnnotationMirror::getElementValues)
                .orElse(Collections.emptyMap());

        return map.values().stream()
                .map(AnnotationValue::toString)
                .map(v -> v.substring(v.lastIndexOf(".") + 1)) // Format as simple name
                .filter(this::isAccessLevel)
                .findFirst();
    }

    private boolean isAccessLevel(String s) {
        Set<String> validAccessLevels = Stream.of("PACKAGE", "NONE", "PRIVATE", "MODULE", "PROTECTED", "PUBLIC")
                .collect(Collectors.collectingAndThen(toSet(), Collections::unmodifiableSet));
        return validAccessLevels.contains(s);
    }

}
