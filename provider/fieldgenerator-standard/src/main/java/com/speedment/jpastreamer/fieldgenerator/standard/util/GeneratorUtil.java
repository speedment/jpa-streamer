package com.speedment.jpastreamer.fieldgenerator.standard.util;

import com.speedment.common.codegen.constant.SimpleParameterizedType;

import java.lang.reflect.Type;
import java.util.*;

public class GeneratorUtil {

    /**
     * Return the java {@link java.lang.Class} object with the specified class name
     *
     * @param className The class name
     * @throws IllegalArgumentException if no class is found with the given name
     */
    public static Class<?> parseType(final String className) {
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
                    return Class.forName(fullName);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + fullName);
                }
        }
    }
}
