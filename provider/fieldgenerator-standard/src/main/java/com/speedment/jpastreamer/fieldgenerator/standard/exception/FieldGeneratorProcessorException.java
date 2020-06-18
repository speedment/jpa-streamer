package com.speedment.jpastreamer.fieldgenerator.standard.exception;

/**
 * A specialized {@code RuntimeException} used to signal errors during the generation process of JPAStreamer fields.
 *
 * @author Julia Gustafsson
 * @since 0.0.9
 */

public class FieldGeneratorProcessorException extends RuntimeException {

    public FieldGeneratorProcessorException() {}

    public FieldGeneratorProcessorException(String message) {
        super(message);
    }

    public FieldGeneratorProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldGeneratorProcessorException(Throwable cause) {
        super(cause);
    }

    public FieldGeneratorProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
