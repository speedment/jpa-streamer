package com.speedment.jpastreamer.projection.internal;

import javax.persistence.TupleElement;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

final class StandardTupleElement<X> implements TupleElement<X> {

    private final Class<? extends X> javaType;
    private final String alias;

    public StandardTupleElement(final Class<? extends X> javaType, final String alias) {
        this.javaType = requireNonNull(javaType);
        this.alias = alias; // Nullable
    }

    @Override
    public Class<? extends X> getJavaType() {
        return javaType;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StandardTupleElement<?> that = (StandardTupleElement<?>) o;

        if (!javaType.equals(that.javaType)) return false;
        return Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {
        int result = javaType.hashCode();
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StandardTupleElement{" +
                "javaType=" + javaType +
                ", alias='" + alias + '\'' +
                '}';
    }
}