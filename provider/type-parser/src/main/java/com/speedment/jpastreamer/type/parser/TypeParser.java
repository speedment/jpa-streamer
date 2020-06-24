package com.speedment.jpastreamer.type.parser;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.jpastreamer.type.parser.exception.TypeParserException;
import com.speedment.jpastreamer.type.parser.util.TypeParserUtil;

import java.lang.reflect.Type;
import java.util.List;

public class TypeParser {

    public static Type render(String s) throws TypeParserException {
        if (!TypeParserUtil.hasBalancedBrackets(s)) {
            throw new TypeParserException("String has imbalanced brackets, hence is not a valid type expression");
        }
        Node node = parseNode(s);
        return render(node);
    }

    private static Type render(Node node) {
        if (node.isLeaf()) {
            return node.type();
        } else {
            Type[] childTypes = node.children().stream()
                    .map(TypeParser::render)
                    .toArray(Type[]::new);

            return SimpleParameterizedType.create(node.type(), childTypes);
        }
    }

    private static Node parseNode(String s) {
        // Base case returns leaf without children
        if (!(s.contains("<") || s.contains(","))) {
            return new Node(SimpleType.create(s));
        } else {
            Node node = new Node(SimpleType.create(s.substring(0, s.indexOf("<"))));
            List<String> params = TypeParserUtil.parameters(s.substring(s.indexOf("<") + 1, s.lastIndexOf(">")));
            for (String param : params) {
                node.addChild(parseNode(param));
            }
            return node;
        }
    }

}
