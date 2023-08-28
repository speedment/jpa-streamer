package com.speedment.jpastreamer.fieldgenerator.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "blogpost",  schema = "publications")
public class BlogPost extends Publication {
    
    @Column(name = "url", nullable = false, updatable = false, columnDefinition = "varchar(255)")
    private String url ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
