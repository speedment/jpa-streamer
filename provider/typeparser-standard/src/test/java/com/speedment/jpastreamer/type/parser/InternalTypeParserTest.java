package com.speedment.jpastreamer.type.parser;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.jpastreamer.type.parser.internal.InternalTypeParser;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class InternalTypeParserTest {

    @Test
    void renderString() {
        String type = "java.lang.String";
        SimpleType expected = SimpleType.create(type);
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderMapOfString() {
        String type = "java.util.Map<java.lang.String>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.Map"), SimpleType.create("java.lang.String"));
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderListOfInteger() {
        String type = "java.util.List<java.lang.Integer>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.List"), SimpleType.create("java.lang.Integer"));
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderListOfListOfNumber() {
        String type = "java.util.List<java.util.List<java.lang.Number>>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.List"), SimpleParameterizedType.create(
                        SimpleType.create("java.util.List"), SimpleType.create("java.lang.Number")));
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderListOfMapOfListOfSetOfDoubleAndInteger() {
        String type = "java.util.List<java.util.Map<java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer>>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.List"), SimpleParameterizedType.create(
                        SimpleType.create("java.util.Map"), SimpleParameterizedType.create(
                                SimpleType.create("java.util.List"), SimpleParameterizedType.create(
                                        SimpleType.create("java.util.Set"), SimpleType.create("java.lang.Double")
                                )
                        ), SimpleType.create("java.lang.Integer")
                )
        );
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderMapOfMapOfQuartetOfStringAndStringAndStringAndStringAndIntegerAndInteger() {
        String type = "java.util.Map<java.util.Map<Quartet<java.lang.String, java.lang.String, java.lang.String, java.lang.String>, java.lang.Integer>, java.lang.Integer>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.Map"), SimpleParameterizedType.create(
                        SimpleType.create("java.util.Map"), SimpleParameterizedType.create(
                                SimpleType.create("Quartet"), SimpleType.create("java.lang.String"), SimpleType.create("java.lang.String"), SimpleType.create("java.lang.String"), SimpleType.create("java.lang.String")),
                                SimpleType.create("java.lang.Integer"))
                        , SimpleType.create("java.lang.Integer")
        );
        Type actual = InternalTypeParser.render(type);
        assertEquals(expected, actual);
    }
}