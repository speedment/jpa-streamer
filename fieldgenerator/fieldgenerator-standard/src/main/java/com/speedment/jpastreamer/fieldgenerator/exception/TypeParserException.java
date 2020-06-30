package com.speedment.jpastreamer.fieldgenerator.exception;

/**
 * A specialized {@code RuntimeException} used to signal errors during the type parsing process.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

public class TypeParserException extends RuntimeException {

    public TypeParserException(String message) {
        super(message);
    }

}
