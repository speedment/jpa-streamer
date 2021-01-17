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
import com.speedment.jpastreamer.fieldgenerator.exception.TypeParserException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * TypeParser that renders Type-objects from the qualified name of a type
 *
 * @author Julia Gustafsson
 * @since 0.1.0
 */

public final class TypeParser {

    public TypeParser() {
    }

    public Type render(String s) throws TypeParserException {
        if (!hasBalancedBrackets(s)) {
            throw new TypeParserException("String " + s + " has imbalanced brackets, hence is not a valid type expression");
        }
        Node root = parseNode(s);
        return render(root);
    }

    private Type render(Node node) {
        if (node.isLeaf()) {
            return node.type();
        } else {
            Type[] childTypes = node.children().stream()
                    .map(this::render)
                    .toArray(Type[]::new);
            return SimpleParameterizedType.create(node.type(), childTypes);
        }
    }

    private Node parseNode(String s) {
        // Base case returns leaf without children
        if (!(s.contains("<") || s.contains(","))) {
            return new Node(SimpleType.create(s));
        } else {
            Node node = new Node(SimpleType.create(s.substring(0, s.indexOf("<"))));
            List<String> params = parameters(s.substring(s.indexOf('<') + 1, s.lastIndexOf('>')));
            for (String param : params) { // Iterate to retain order of elements
                node.addChild(parseNode(param));
            }
            return node;
        }
    }

    private boolean hasBalancedBrackets(String s) {
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

    private boolean hasBalancedBracketsAtPos(String s, int pos) {
        return hasBalancedBrackets(s.substring(0,pos));
    }

    private List<String> parameters(String s) {
        List<String> children = new ArrayList<>();
        if (!s.contains(",")) {
            children.add(s);
            return children;
        }
        int pos = 0;
        String str = s;
        while (str.substring(pos).contains(",")) {
            pos = str.indexOf(',', pos);
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