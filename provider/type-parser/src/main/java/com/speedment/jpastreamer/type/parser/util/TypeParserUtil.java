package com.speedment.jpastreamer.type.parser.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TypeParserUtil {

    public static long charCount(String s, char c) {
        return s.chars().filter(ch -> ch == c).count();
    }

    public static boolean hasBalancedBrackets(String s) {
        int pos = 0;
        int openBr = 0;
        while (pos < s.length()) {
            if (openBr == -1) {
                // Brackets are not facing each other e.g. ><
                return false;
            }
            if (s.charAt(pos) == '<') {
                openBr++;
            } else if (s.charAt(pos) == '>') {
                openBr--;
            }
            pos++;
        }
        return openBr == 0;
    }

    public static boolean hasBalancedBracketsAtPos(String s, int pos) {
        return hasBalancedBrackets(s.substring(0,pos));
    }

    public static boolean isSingleChild(String s) {
        if (!s.contains(",")) {
            return true;
        } {

            return false;
        }
    }

    public static void splitString(String s){
        String[] split = s.split("[\\<||\\>]");
        Stream.of(split)
                .forEach(System.out::println);
    }

    /**
     *
     * @param s     The input string to be parsed
     * @return      A list of
     */
    public static List<String> parameters(String s) {
        List<String> children = new ArrayList<>();
        if (!s.contains(",")) {
            children.add(s);
            return children;
        }
        int pos = 0;
        String str = s;
        while (str.substring(pos).contains(",")) {
            pos = str.indexOf(",", pos);
            if (hasBalancedBracketsAtPos(str, pos)) {
                children.add(str.substring(0, pos).trim());
                str = str.substring(pos + 1);
                pos = 0;
            } else {
                pos++;
            }
        }
        children.add(str.trim());
        return children;
    }

    public static boolean isParameterizedType(String s) {
        return s.contains("<");
    }

}
