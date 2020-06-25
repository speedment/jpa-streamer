package com.speedment.jpastreamer.fieldgenerator.standard.util;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;

import java.util.stream.Stream;

public class GeneratorUtil {

    /**
     * Return the java {@link java.lang.Class} object with the specified class name
     *
     * @param className The class name
     * @throws IllegalArgumentException if no class is found with the given name
     */
    public static java.lang.Class<?> parseType(final String className) {
        switch (className) {
            case "boolean":
                return boolean.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            case "void":
                return void.class;
            default:
                String fullName;
                if (!className.contains(".")) {
                    fullName = "java.lang.".concat(className);
                } else if (className.contains("<")) {
                    fullName = className.substring(0, className.indexOf("<"));
                } else if (className.contains("[]")) {
                    fullName = ""; // TODO SUPPORT ARRAY
                } else {
                    fullName = className;
                }
                try {
                    return java.lang.Class.forName(fullName);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + fullName);
                }
        }
    }

    public static void importType(String qualifiedTypeName, Class clazz) {
        Stream.of(qualifiedTypeName.split("[<|>|,]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .map(SimpleType::create)
                .forEach(st -> clazz.add(Import.of(st)));
    }


}
