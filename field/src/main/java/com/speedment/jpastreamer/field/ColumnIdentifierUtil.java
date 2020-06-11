package com.speedment.jpastreamer.field;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

public final class ColumnIdentifierUtil {

    private static final ColumnIdentifier<?> INSTANCE = new ColumnIdentifier<Object>() {
        @Override
        public String getColumnId() {
            return "";
        }

        @Override
        public String getDbmsId() {
            return "";
        }

        @Override
        public String getSchemaId() {
            return "";
        }

        @Override
        public String getTableId() {
            return "";
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> ColumnIdentifier<T> of() {
        return (ColumnIdentifier<T>) INSTANCE;
    }

}