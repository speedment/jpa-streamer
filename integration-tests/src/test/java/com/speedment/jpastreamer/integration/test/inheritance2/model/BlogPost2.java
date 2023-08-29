package com.speedment.jpastreamer.integration.test.inheritance2.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BlogPost")
public class BlogPost2 extends Publication2 {
    
    @Column(name = "url", columnDefinition = "varchar(255)")
    private String url ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
