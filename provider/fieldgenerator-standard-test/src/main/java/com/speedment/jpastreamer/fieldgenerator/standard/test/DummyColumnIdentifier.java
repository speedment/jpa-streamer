package com.speedment.jpastreamer.fieldgenerator.standard.test;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

public enum  DummyColumnIdentifier implements ColumnIdentifier<Film> {
    INSTANCE;

    @Override
    public String getColumnId() {
        return null;
    }

    @Override
    public String getDbmsId() {
        return null;
    }

    @Override
    public String getSchemaId() {
        return null;
    }

    @Override
    public String getTableId() {
        return null;
    }

}