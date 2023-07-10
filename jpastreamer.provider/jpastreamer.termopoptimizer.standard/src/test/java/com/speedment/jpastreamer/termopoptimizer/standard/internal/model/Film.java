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
package com.speedment.jpastreamer.termopoptimizer.standard.internal.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "film", schema = "sakila")
public class Film {
    public Film() {
    }

    public Film(short filmId) {
        this.filmId = filmId;
    }

    public Film(String description) {
        this.description = description;
    }

    public Film(short filmId, String title, String description) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
    }

    public Film(short filmId, String description) {
        this.filmId = filmId;
        this.description = description;
    }

    @Id
    @Column(name = "film_id", columnDefinition = "SMALLINT UNSIGNED")
    private short filmId;

    @Basic
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @Basic
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Basic
    @Column(name = "rental_duration", columnDefinition = "YEAR")
    private short rentalDuration;
    @Basic
    @Column(name = "language_id", columnDefinition = "SMALLINT UNSIGNED")
    private short languageId;
    @Basic
    @Column(name = "rental_rate", columnDefinition = "DECIMAL(4,2)")
    private BigDecimal rentalRate;

    @Basic
    @Column(name = "length", columnDefinition = "SMALL UNSIGNED")
    private Short length;

    @Basic
    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    private String rating;

    @Basic
    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;
    
    public short getFilmId() {
        return filmId;
    }

    public void setFilmId(short filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getLanguageId() {
        return languageId;
    }

    public void setLanguageId(short languageId) {
        this.languageId = languageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (filmId != film.filmId) return false;
        if (rentalDuration != film.rentalDuration) return false;
        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        if (description != null ? !description.equals(film.description) : film.description != null) return false;
        if (rentalRate != null ? !rentalRate.equals(film.rentalRate) : film.rentalRate != null) return false;
        if (length != null ? !length.equals(film.length) : film.length != null) return false;
        if (replacementCost != null ? !replacementCost.equals(film.replacementCost) : film.replacementCost != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) filmId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) rentalDuration;
        result = 31 * result + (rentalRate != null ? rentalRate.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (replacementCost != null ? replacementCost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rentalDuration=" + rentalDuration +
                ", languageId=" + languageId +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", rating='" + rating + '\'' +
                ", replacementCost=" + replacementCost +
                '}';
    }
}
