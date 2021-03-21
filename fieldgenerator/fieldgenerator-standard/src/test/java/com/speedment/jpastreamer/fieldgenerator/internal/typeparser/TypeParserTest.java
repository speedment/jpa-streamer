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
package com.speedment.jpastreamer.fieldgenerator.internal.typeparser;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class TypeParserTest {

    final TypeParser typeParser = new TypeParser();

    @Test
    void renderString() {
        String type = "java.lang.String";
        SimpleType expected = SimpleType.create(type);
        Type actual = typeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderMapOfString() {
        String type = "java.util.Map<java.lang.String>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.Map"), SimpleType.create("java.lang.String"));
        Type actual = typeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderListOfInteger() {
        String type = "java.util.List<java.lang.Integer>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.List"), SimpleType.create("java.lang.Integer"));
        Type actual = typeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderListOfListOfNumber() {
        String type = "java.util.List<java.util.List<java.lang.Number>>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.List"), SimpleParameterizedType.create(
                        SimpleType.create("java.util.List"), SimpleType.create("java.lang.Number")));
        Type actual = typeParser.render(type);
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
        Type actual = typeParser.render(type);
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
        Type actual = typeParser.render(type);
        assertEquals(expected, actual);
    }

    @Test
    void renderMapOfMapOfListOfSetOfDoubleAndIntegerAndLong() {
        String type = "java.util.Map<java.util.Map<java.util.List<java.util.Set<java.lang.Double>>,java.lang.Integer>,java.lang.Long>";
        SimpleParameterizedType expected = SimpleParameterizedType.create(
                SimpleType.create("java.util.Map"), SimpleParameterizedType.create(
                        SimpleType.create("java.util.Map"), SimpleParameterizedType.create(
                                SimpleType.create("java.util.List"), SimpleParameterizedType.create(
                                        SimpleType.create("java.util.Set"), SimpleType.create("java.lang.Double"))),
                        SimpleType.create("java.lang.Integer"))
                , SimpleType.create("java.lang.Long")
        );
        Type actual = typeParser.render(type);
        assertEquals(expected, actual);
    }

}
