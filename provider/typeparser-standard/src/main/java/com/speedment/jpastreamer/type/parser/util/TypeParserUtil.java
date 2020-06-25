package com.speedment.jpastreamer.type.parser.util;

import java.util.ArrayList;
import java.util.List;

public final class TypeParserUtil {

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

}
