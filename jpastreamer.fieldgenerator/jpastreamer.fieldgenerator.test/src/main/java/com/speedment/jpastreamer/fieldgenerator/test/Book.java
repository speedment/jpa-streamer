package com.speedment.jpastreamer.fieldgenerator.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books",  schema = "publications")
public class Book extends Publication {

    @Column(name = "pages", nullable = false, updatable = false, columnDefinition = "int(6)")
    private Integer pages;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
