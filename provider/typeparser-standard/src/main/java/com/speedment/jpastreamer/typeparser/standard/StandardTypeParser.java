package com.speedment.jpastreamer.typeparser.standard;

import com.speedment.jpastreamer.typeparser.standard.exception.TypeParserException;
import com.speedment.jpastreamer.typeparser.standard.internal.InternalTypeParser;
import java.lang.reflect.Type;

/**
 * JPAStreamer standard type parser to render a qualified type name to a corresponding {@code Type}.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

public final class StandardTypeParser {

    /**
     * Renders an input String as a type, e.g. java.util.Map<java.lang.Integer, java.lang.String>>
     * to the corresponding {@code Type} object.
     *
     * @param qualifiedTypeName     the qualified name of the type
     * @return                      a Type corresponding to the given type Name
     *
     * @throws TypeParserException  if the qualifiedTypeName does not qualify as a type name
     */
    public static Type render(String qualifiedTypeName) {
        return InternalTypeParser.render(qualifiedTypeName);
    }

}