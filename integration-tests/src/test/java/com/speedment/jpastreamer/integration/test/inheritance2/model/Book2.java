package com.speedment.jpastreamer.integration.test.inheritance2.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Book")
public class Book2 extends Publication2 {

    @Column(name = "pages", columnDefinition = "int(6)")
    private Integer pages;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
