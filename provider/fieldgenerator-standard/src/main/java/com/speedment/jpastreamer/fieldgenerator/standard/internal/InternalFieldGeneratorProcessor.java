package com.speedment.jpastreamer.fieldgenerator.standard.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.controller.AlignTabs;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.internal.java.view.FieldView;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.jpastreamer.field.*;
import com.speedment.jpastreamer.fieldgenerator.standard.exception.FieldGeneratorProcessorException;
import com.speedment.jpastreamer.typeparser.standard.StandardTypeParser;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.persistence.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.Enum;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.util.Formatting.*;
import static com.speedment.jpastreamer.fieldgenerator.standard.util.GeneratorUtil.*;

/**
 * JPAStreamer standard annotation processor that generates fields for classes annotated
 * with {@code Entity}.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

public final class InternalFieldGeneratorProcessor extends AbstractProcessor {

    protected static final String GETTER_METHOD_PREFIX = "get";

    private static final Generator generator = Generator.forJava();

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
        writer.write(new JavaGenerator().on(file).get());
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
        Column col = field.getAnnotation(Column.class);

        java.lang.Class fieldClass;
        try {
            messager.printMessage(Diagnostic.Kind.NOTE, "parsing type: " + fieldType(field).getTypeName());
            fieldClass = parseType(fieldType(field).getTypeName());
        } catch (IllegalArgumentException e) {
            throw new FieldGeneratorProcessorException("Type with name " + fieldType(field).getTypeName() + " was not found.");
        }

        Type referenceType = referenceType(field, fieldClass, entityName);

        // Begin building the field value parameters
        final List<Value<?>> fieldParams = new ArrayList<>();

        // Add table entity
        fieldParams.add(Value.ofReference(entityName + ".class"));

        // Add db column name if stated, else fall back on entity field name
        fieldParams.add(Value.ofText((col != null && !col.name().isEmpty()) ? col.name() : fieldName));

        // Add getter method reference
        fieldParams.add(Value.ofReference(
                entityName + "::" + GETTER_METHOD_PREFIX + ucfirst(fieldName)));

        if (Enum.class.isAssignableFrom(fieldClass)) {
            String fieldTypeName = shortName(fieldType(field).getTypeName());

            // Add enum class
            fieldParams.add(Value.ofReference(fieldTypeName + ".class"));
        } else {
            // Add the 'unique' boolean to the end for all field but enum
            fieldParams.add(Value.ofBoolean(col != null && col.unique()));
        }

        Field field1 = Field.of(fieldName, referenceType);
        FieldView fieldView = new FieldView();
        Optional<String> output = fieldView.transform(generator, field1);

        messager.printMessage(Diagnostic.Kind.NOTE, "Rendering reference type to: " + output + " for field with name: " + field.getSimpleName());
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

    private Type referenceType(Element field, java.lang.Class fieldClass, String entityName) throws FieldGeneratorProcessorException {
        Type fieldType = fieldType(field);
        Type entityType = SimpleType.create(entityName);
        final Type type;

        try {
            if (fieldClass.isPrimitive()) {
                type = primitiveFieldType(fieldType, entityType, fieldClass);
            } else if (Enum.class.isAssignableFrom(fieldClass)) {
                type = SimpleParameterizedType.create(
                        EnumField.class,
                        entityType,
                        fieldType);
            } else if (Comparable.class.isAssignableFrom(fieldClass) && field.getAnnotation(Lob.class) == null) {
                type = String.class.equals(fieldClass) ?
                        SimpleParameterizedType.create(
                                StringField.class,
                                entityType) :
                        SimpleParameterizedType.create(
                                ComparableField.class,
                                entityType,
                                fieldType);
                messager.printMessage(Diagnostic.Kind.NOTE, "Parsing field type: " + type.getClass() + " for field " + field.getSimpleName());
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
        messager.printMessage(Diagnostic.Kind.NOTE, "Using type parser to parse: " + field.asType().toString());
        return StandardTypeParser.render(field.asType().toString());
    }

    private Optional<Type> timeType(TemporalType temporalType) {
        Objects.requireNonNull(temporalType, "Temporal type cannot be null");
        switch (temporalType) {
            case DATE:
                return Optional.of(java.sql.Date.class);
            case TIME:
                return Optional.of(java.sql.Time.class);
            case TIMESTAMP:
                return Optional.of(java.sql.Timestamp.class);
            default:
                throw new FieldGeneratorProcessorException("Unknown temporal type " + temporalType);
        }
    }

    private Optional<Type> timeType(String columnDefinition) {
        Objects.requireNonNull(columnDefinition, "Column definition type cannot be null");
        switch (columnDefinition) {
            case "DATE":
                return Optional.of(java.sql.Date.class);
            case "TIME":
                return Optional.of(java.sql.Time.class);
            case "TIMESTAMP":
                return Optional.of(java.sql.Timestamp.class);
            default:
                throw new FieldGeneratorProcessorException("Cannot process information about database time type from columnDefinition: " +  columnDefinition);
        }
    }

    private Type primitiveFieldType(Type fieldType, Type entityType, java.lang.Class c) throws UnsupportedOperationException {
        java.lang.Class fieldClass;
        switch (c.getSimpleName()) {
            case "int":
                fieldClass = IntField.class;
                break;
            case "byte":
                fieldClass = ByteField.class;
                break;
            case "short":
                fieldClass = ShortField.class;
                break;
            case "long":
                fieldClass = LongField.class;
                break;
            case "float":
                fieldClass = FloatField.class;
                break;
            case "double":
                fieldClass = DoubleField.class;
                break;
            case "char":
                fieldClass = CharField.class;
                break;
            case "boolean":
                fieldClass = BooleanField.class;
                break;
            default : throw new UnsupportedOperationException(
                    "Unknown primitive type: '" + fieldType.getTypeName() + "'."
            );
        }
        return SimpleParameterizedType.create(
                fieldClass,
                entityType
        );
    }

}