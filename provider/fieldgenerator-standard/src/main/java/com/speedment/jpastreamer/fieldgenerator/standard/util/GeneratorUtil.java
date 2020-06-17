package com.speedment.jpastreamer.fieldgenerator.standard.util;

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
                String fullName = className.contains(".") ? className : "java.lang.".concat(className);
                try {
                    return Class.forName(fullName);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + fullName);
                }
        }
    }

}
