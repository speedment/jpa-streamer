package com.speedment.jpastreamer.field.trait;

/**
 * @author Julia Gustafsson
 */
public interface HasColumnName {

    /**
     * Returns the database column name of this field.
     *
     * @return  the database column name
     */
    String columnName();

}
