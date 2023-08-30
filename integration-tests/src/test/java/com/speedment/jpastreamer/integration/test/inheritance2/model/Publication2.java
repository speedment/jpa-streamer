/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.integration.test.inheritance2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "publication_type")
public abstract class Publication2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "int(6)")
    protected Long id;
    
    @Column(name = "publishing_date", nullable = false, columnDefinition = "date")
    protected LocalDateTime publishingDate;
    
    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
    private String title;
    
    @Version
    @Column(name = "version", nullable = false, columnDefinition = "int(6)")
    private Integer version;
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "publication_author", 
            joinColumns = { @JoinColumn(name = "publication_id", referencedColumnName = "id")}, 
            inverseJoinColumns = { @JoinColumn(name = "author_id", referencedColumnName = "id")}
    )
    private Set<Author> authors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDateTime publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set getAuthors() {
        return authors;
    }

    public void setAuthors(Set authors) {
        this.authors = authors;
    }
}
