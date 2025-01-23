package com.speedment.jpastreamer.fieldgenerator.test;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Versionable {

    @Version
    @Column(name = "version", nullable = false, updatable = false, columnDefinition = "int(6)")
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
