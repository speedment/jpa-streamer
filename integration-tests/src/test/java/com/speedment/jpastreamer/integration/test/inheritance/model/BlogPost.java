package com.speedment.jpastreamer.integration.test.inheritance.model;

import jakarta.persistence.*;

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
