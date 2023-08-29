package com.speedment.jpastreamer.integration.test.inheritance2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author",  schema = "publications2")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "int(6)")
    protected Long id;

    @Column(name = "firstname", columnDefinition = "varchar(255)")
    private String firstname;

    @Column(name = "lastname", columnDefinition = "varchar(255)")
    private String lastname;

    @ManyToMany(mappedBy = "authors")
    private List<Publication2> publications = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Publication2> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication2> publications) {
        this.publications = publications;
    }
}
