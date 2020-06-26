package com.speedment.jpastreamer.fieldgenerator.standard.util;

import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import com.speedment.jpastreamer.type.parser.util.TypeParserUtil;

import java.util.HashMap;
import java.util.Map;
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
                java.lang.Class clazz;
                String qualifiedName = "";
                if (className.endsWith("[]")) {
                    long arrayDepth = TypeParserUtil.charCount(className, '[');
                    for (int i = 0; i < arrayDepth; i++) {
                        qualifiedName = qualifiedName.concat("[");
                    }
                    String typeName = className.substring(0, className.indexOf("["));
                    Character encoding = encodingOfType(typeName);
                    // E.g. [[Ljava.lang.String; is the qualified name of String[][] and [[[[B is the qualified name of byte[][][][]
                    qualifiedName = qualifiedName.concat(encoding.toString() + (encoding == 'L' ? typeName + ";" : ""));
                } else if (className.contains("<")) {
                    qualifiedName = className.substring(0, className.indexOf("<"));
                }
                else {
                    qualifiedName = className;
                }
                try {
                    return java.lang.Class.forName(qualifiedName);
                } catch (ClassNotFoundException ex) {
                    throw new IllegalArgumentException("Class not found: " + qualifiedName);
                }
        }
    }

    private static Character encodingOfType(String typeName) {
        switch (typeName) {
            case "int": return 'I';
            case "double": return 'D';
            case "float": return 'F';
            case "short": return 'S';
            case "long": return 'J';
            case "char": return 'C';
            case "byte": return 'B';
            case "boolean": return 'Z';
            default: return 'L';
        }
    }

}
