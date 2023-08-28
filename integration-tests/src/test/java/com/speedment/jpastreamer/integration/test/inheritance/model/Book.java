package com.speedment.jpastreamer.integration.test.inheritance.model;

import jakarta.persistence.*;

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
