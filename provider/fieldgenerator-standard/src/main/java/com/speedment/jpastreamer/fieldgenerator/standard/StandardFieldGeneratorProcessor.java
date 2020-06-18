package com.speedment.jpastreamer.fieldgenerator.standard;

import com.google.auto.service.AutoService;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.jpastreamer.fieldgenerator.standard.exception.FieldGeneratorProcessorException;
import com.speedment.jpastreamer.fieldgenerator.standard.util.GeneratorUtil;
import com.speedment.runtime.field.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.Enum;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.common.codegen.util.Formatting.ucfirst;

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

    protected static final String GETTER_METHOD_PREFIX = "get";
    protected static final String SETTER_METHOD_PREFIX = "set";

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

        String entityName = shortName(annotatedElement.asType().toString());
        String genEntityName = entityName + "$";
        String fullEntityName = annotatedElement.asType().toString() + "$";

        PackageElement packageElement = processingEnvironment.getElementUtils().getPackageOf(annotatedElement);
        String packageName;
        if (packageElement.isUnnamed()) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Class " + entityName + "has an unnamed package.");
            packageName = "";
        } else {
            packageName = packageElement.getQualifiedName().toString();
        }

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fullEntityName);
        Writer writer = builderFile.openWriter();

        File file = generatedEntity(enclosedFields, entityName, genEntityName, packageName);
        writer.write(new JavaGenerator().on(file).get());
        writer.close();
    }

    private File generatedEntity(Set<? extends Element> enclosedFields, String annotatedClassName, String genEntityName, String packageName) {

        File file = packageName.isEmpty() ?
                File.of(genEntityName + ".java") :
                File.of(packageName + "/" + genEntityName + ".java");
        Class clazz = Class.of(genEntityName).public_();

        enclosedFields
                .forEach(field -> {
                    addFieldToClass(field, clazz, annotatedClassName);
                });

        file.add(clazz);
        return file;
    }

    private void addFieldToClass(Element field, Class clazz, String entityName) {
        String fieldName = field.getSimpleName().toString();
        Type referenceType = referenceType(field, entityName);

        // Begin building the field value parameters
        final List<Value<?>> fieldParams = fieldParams(field, fieldName, entityName);

        clazz.add(Import.of(referenceType));
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

    private List<Value<?>> fieldParams(Element field, String fieldName, String entityName) {
        final List<Value<?>> list = new ArrayList<>();

        list.add(Value.ofReference("DummyColumnIdentifier.INSTANCE"));

        // Add getter method reference //TODO Should we really assume getters and setters are present?
        list.add(Value.ofReference(
                entityName + "::" + GETTER_METHOD_PREFIX + ucfirst(fieldName)));

        // Add setter method reference
        list.add(Value.ofReference(
                entityName + "::" + SETTER_METHOD_PREFIX + ucfirst(fieldName)));

        // Currently no type mapper is added
        list.add(Value.ofReference("null"));

        // Add the 'unique' boolean to the end
        list.add(Value.ofBoolean(field.getAnnotation(Column.class).unique()));

        return list;
    }

    private Type referenceType(Element field, String entityName) throws FieldGeneratorProcessorException {

        Type fieldType = SimpleType.create(field.asType().toString());
        Type entityType = SimpleType.create(entityName);
        final Type type;

        try {
            java.lang.Class c = GeneratorUtil.parseType(field.asType().toString());
            if (c.isPrimitive()) {
                type = primitiveType(fieldType, entityType, c);
            } else if (Comparable.class.isAssignableFrom(c)) {
                type = String.class.equals(c) ?
                        SimpleParameterizedType.create(StringField.class, entityType, String.class) :
                        SimpleParameterizedType.create(ComparableField.class, entityType, fieldType, fieldType);
            } else {
                type = SimpleParameterizedType.create(ReferenceField.class, entityType, fieldType, fieldType);
            }
        } catch (IllegalArgumentException e) {
            throw new FieldGeneratorProcessorException("Type " + fieldType.getTypeName() + " was not found.");
        } catch (UnsupportedOperationException e) {
            throw new FieldGeneratorProcessorException("Primitive type " + fieldType.getTypeName() + " could not be parsed.");
        }

        return type;
    }

    private Type primitiveType(Type fieldType, Type entityType, java.lang.Class c) throws UnsupportedOperationException {
        Type type;
        switch (c.getSimpleName()) {
            case "int":
                type = SimpleParameterizedType.create(
                        IntField.class,
                        entityType,
                        Integer.class
                );
                break;
            case "byte":
                type = SimpleParameterizedType.create(
                        ByteField.class,
                        entityType,
                        Byte.class
                );
                break;
            case "short":
                type = SimpleParameterizedType.create(
                        ShortField.class,
                        entityType,
                        fieldType,
                        Short.class
                );
                break;
            case "long":
                type = SimpleParameterizedType.create(
                        LongField.class,
                        entityType,
                        Long.class
                );
                break;
            case "float":
                type = SimpleParameterizedType.create(
                        FloatField.class,
                        entityType,
                        Float.class
                );
                break;
            case "double":
                type = SimpleParameterizedType.create(
                        DoubleField.class,
                        entityType,
                        Double.class
                );
                break;
            case "char":
                type = SimpleParameterizedType.create(
                        CharField.class,
                        entityType,
                        Character.class
                );
                break;
            case "boolean":
                type = SimpleParameterizedType.create(
                        BooleanField.class,
                        entityType,
                        Boolean.class
                );
                break;
            case "enum":
                type = SimpleParameterizedType.create(
                        EnumField.class,
                        entityType,
                        Enum.class,
                        Enum.class
                );
                break;
            default : throw new UnsupportedOperationException(
                    "Unknown primitive type: '" + fieldType.getTypeName() + "'."
            );
        }
        return type;
    }
}