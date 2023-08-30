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
package com.speedment.jpastreamer.integration.test.inheritance.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "int(6)")
    protected Integer id;
    
    @Column(name = "publishing_date", nullable = false, updatable = false, columnDefinition = "date")
    protected LocalDateTime publishingDate;
    
    @Column(name = "title", nullable = false, updatable = false, columnDefinition = "varchar(255)")
    private String title;
    
    @Version
    @Column(name = "version", nullable = false, updatable = false, columnDefinition = "int(6)")
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
