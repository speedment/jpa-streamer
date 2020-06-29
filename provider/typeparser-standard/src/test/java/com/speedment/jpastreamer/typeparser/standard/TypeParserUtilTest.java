package com.speedment.jpastreamer.typeparser.standard;

import com.speedment.jpastreamer.typeparser.standard.util.TypeParserUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeParserUtilTest {

    @Test
    void charCount() {
        assertEquals(4, TypeParserUtil.charCount("elephant has large ears", 'e'));
    }

    @Test
    void hasBalancedBracketsTrue() {
        assertTrue(TypeParserUtil.hasBalancedBrackets(""));
    }

    @Test
    void hasBalancedBracketsTrue2() {
        assertTrue(TypeParserUtil.hasBalancedBrackets("java.util.List<java.util.Map<java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer>>"));
    }

    @Test
    void hasBalancedBracketsFalse() {
        assertFalse(TypeParserUtil.hasBalancedBrackets("java.util.List<java.util.Map<java.util.List<java.util.Set<java.lang.Double>, java.lang.Integer>>"));
    }

    @Test
    void hasBalancedBracketsFalse2() {
        assertFalse(TypeParserUtil.hasBalancedBrackets("java.util.List>java.util.Map<java.util.List<java.util.Set<java.lang.Double<, java.lang.Integer>>"));
    }

    @Test
    void hasBalancedBracketsFalse3() {
        assertFalse(TypeParserUtil.hasBalancedBrackets(">><<"));
    }

    @Test
    void hasBalancedBracketsAtPos() {
        String s = "java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer";
        int pos = s.indexOf(",");
        assertTrue(TypeParserUtil.hasBalancedBracketsAtPos(s,pos));
    }

    @Test
    void hasBalancedBracketsAtPosFalse() {
        String s = "java.lang.Double, java.lang.Integer";
        int pos = s.indexOf(",");
        assertTrue(TypeParserUtil.hasBalancedBracketsAtPos(s,pos));
    }

    @Test
    void parameters() {
        String s = "java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer";
        List<String> expected = new ArrayList<>();
        expected.add("java.util.List<java.util.Set<java.lang.Double>>");
        expected.add("java.lang.Integer");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }

    @Test
    void parameters2() {
        String s = "java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>, java.lang.Integer";
        List<String> expected = new ArrayList<>();
        expected.add("java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>");
        expected.add("java.lang.Integer");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }

    @Test
    void parameters3() {
        String s = "java.util.List, java.lang.Integer";
        List<String> expected = new ArrayList<>();
        expected.add("java.util.List");
        expected.add("java.lang.Integer");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }

    @Test
    void parameters4() {
        String s = "java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>, java.lang.Integer, java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>";
        List<String> expected = new ArrayList<>();
        expected.add("java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>");
        expected.add("java.lang.Integer");
        expected.add("java.util.List<java.util.Map<java.lang.Double, java.lang.Integer>>");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }

    @Test
    void parameters5(){
        String s = "java.util.List<java.util.Map<java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer>>";
        List<String> expected = new ArrayList<>();
        expected.add("java.util.List<java.util.Map<java.util.List<java.util.Set<java.lang.Double>>, java.lang.Integer>>");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }

    @Test
    void parameters6() {
        String s = "java.lang.Integer";
        List<String> expected = new ArrayList<>();
        expected.add("java.lang.Integer");
        assertEquals(expected, TypeParserUtil.parameters(s));
    }
}