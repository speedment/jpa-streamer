package com.speedment.jpastreamer.fieldgenerator.standard.exception;

/**
 * {@code RuntimeException} used to signal errors during generation of JPAStreamer fields.
 *
 * @author Julia Gustafsson
 */

public class StandardFieldGeneratorException extends RuntimeException {

    public StandardFieldGeneratorException() {
        super();
    }

    public StandardFieldGeneratorException(String message) {
        super(message);
    }

}
