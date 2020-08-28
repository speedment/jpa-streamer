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
import java.util.stream.Collectors;

import static com.speedment.common.codegen.util.Formatting.*;

/**
 * JPAStreamer standard annotation processor that generates fields for classes annotated
 * with {@code Entity}.
 *
 * @author Julia Gustafsson
 * @since 0.1.0
 */

public final class InternalFieldGeneratorProcessor extends AbstractProcessor {

    protected static final String GETTER_METHOD_PREFIX = "get";

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

        if(annotations.size() == 0 || roundEnv.processingOver()) {
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
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });

        return true;
    }

    void generateFields(Element annotatedElement, Writer writer) throws IOException {

        // Retrieve all declared fields of the annotated class
        Set<? extends Element> enclosedFields = annotatedElement.getEnclosedElements().stream()
                .filter(ee -> ee.getKind().isField()
                        && !ee.getModifiers().contains(Modifier.FINAL)) // Ignore immutable fields
                .collect(Collectors.toSet());

        String entityName = shortName(annotatedElement.asType().toString());
        String genEntityName = entityName + "$";

        PackageElement packageElement = processingEnvironment.getElementUtils().getPackageOf(annotatedElement);
        String packageName;
        if (packageElement.isUnnamed()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Class " + entityName + "has an unnamed package.");
            packageName = "";
        } else {
            packageName = packageElement.getQualifiedName().toString();
        }

        File file = generatedEntity(enclosedFields, entityName, genEntityName, packageName);
        writer.write(generator.on(file).get());
    }

    private File generatedEntity(Set<? extends Element> enclosedFields, String entityName, String genEntityName, String packageName) {
        File file = packageName.isEmpty() ?
                File.of(genEntityName + ".java") :
                File.of(packageName + "/" + genEntityName + ".java");
        Class clazz = Class.of(genEntityName).public_()
                .set(Javadoc.of(
                        "The generated base for entity {@link " + entityName + "} representing entities of the"
                                + " {@code " + lcfirst(entityName) + "}-table in the database." +
                                nl() + "<p> This file has been automatically generated by JPAStreamer."
                ).author("JPAStreamer"));

        enclosedFields
                .forEach(field -> {
                    addFieldToClass(field, clazz, entityName);
                });

        file.add(clazz);
        file.call(new AutoImports(generator.getDependencyMgr())).call(new AlignTabs<>());
        return file;
    }

    private void addFieldToClass(Element field, Class clazz, String entityName) {
        String fieldName = field.getSimpleName().toString();

        Type referenceType = referenceType(field, entityName);

        // Begin building the field value parameters
        final List<Value<?>> fieldParams = new ArrayList<>();

        // Add table entity
        fieldParams.add(Value.ofReference(entityName + ".class"));

        // Add the attribute name
        fieldParams.add(Value.ofText(fieldName));

        // Add getter method reference
        fieldParams.add(Value.ofReference(
                entityName + "::" + GETTER_METHOD_PREFIX + ucfirst(fieldName)));

        TypeElement typeElement = elementUtils.getTypeElement(fieldType(field).getTypeName());
        TypeMirror enumType = elementUtils.getTypeElement("java.lang.Enum").asType();

        if (typeElement != null && typeUtils.isAssignable(typeElement.asType(), enumType)) {
            String fieldTypeName = shortName(fieldType(field).getTypeName());

            // Add enum class
            fieldParams.add(Value.ofReference(fieldTypeName + ".class"));
        } else {
            // Add the 'unique' boolean to the end for all field but enum
            Column col = field.getAnnotation(Column.class);
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
                        "This Field corresponds to the {@link " + entityName + "} field that can be obtained using the "
                                + "{@link " + entityName + "#get" + ucfirst(fieldName) + "()} method."
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
            default : throw new UnsupportedOperationException(
                    "Unknown primitive type: '" + fieldType.getTypeName() + "'."
            );
        }
        return SimpleParameterizedType.create(
                primitiveFieldType,
                entityType
        );
    }

}
