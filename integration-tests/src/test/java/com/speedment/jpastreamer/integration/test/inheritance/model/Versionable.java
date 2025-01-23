package com.speedment.jpastreamer.integration.test.inheritance.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

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
