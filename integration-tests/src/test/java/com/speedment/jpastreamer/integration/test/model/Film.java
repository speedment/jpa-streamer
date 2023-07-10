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
package com.speedment.jpastreamer.integration.test.model;

import com.speedment.jpastreamer.integration.test.model.groups.GroupA;
import com.speedment.jpastreamer.integration.test.model.groups.GroupB;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "film", schema = "sakila")
public class Film implements Serializable {

    public Film() {}

    public Film(int filmId) {
        this.filmId = filmId;
    }

    public Film(Integer filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false, updatable = false, columnDefinition = "smallint(5)")
    private Integer filmId;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
    //@NotEmpty(message = "Title may not be empty", groups = {GroupA.class, GroupB.class})
    @NotNull(message = "Title may not be null", groups = {GroupA.class, GroupB.class})
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name="language_id", nullable = false, updatable = false, insertable = false)
    private Language language;

/*    @Column(name="language_id", nullable = false)
    private Language originalLanguageId;*/

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "film_actor",
            joinColumns = { @JoinColumn(name = "film_id") },
            inverseJoinColumns = { @JoinColumn(name = "actor_id") }
    )
    private List<Actor> actors = new ArrayList<>();

    @Column(name = "rental_duration", columnDefinition = "smallint(5)")
    private Integer rentalDuration;

    @Column(name = "rental_rate", columnDefinition = "decimal(4,2)")
    private Float rentalRate;

    @Column(name = "length", columnDefinition = "smallint(5)")
    //@NotEmpty(message = "Length may not be empty", groups = {GroupA.class, GroupB.class})
    //@NotEmpty(message = "Length may not be empty", groups = {GroupA.class})
    //@NotNull(message = "Length may not be null", groups = {GroupA.class, GroupB.class})
    @Size(min = 1, max = 300, message = "Length must be between 1 and 300 minutes characters long")
    private Integer length;

    @Column(name = "replacement_cost", columnDefinition = "decimal(5,2)")
    private Float replacementCost;

    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    private String rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
    private String specialFeatures; // Should be Set<String>

    @Column(name = "last_update", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime lastUpdate;

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public void setOriginalLanguage(Language language) {
        this.language = language;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public Float getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(Float rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Float getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(Float replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", language=" + language +
                ", actors=" + actors +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating='" + rating + '\'' +
                ", specialFeatures='" + specialFeatures + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (!Objects.equals(filmId, film.filmId)) return false;
        if (!Objects.equals(title, film.title)) return false;
        if (!Objects.equals(description, film.description)) return false;
        //if (originalLanguageId != null ? !originalLanguageId.equals(film.originalLanguageId) : film.originalLanguageId != null) return false;
        if (!Objects.equals(rentalDuration, film.rentalDuration))
            return false;
        if (!Objects.equals(rentalRate, film.rentalRate)) return false;
        if (!Objects.equals(length, film.length)) return false;
        if (!Objects.equals(replacementCost, film.replacementCost))
            return false;
        if (!Objects.equals(rating, film.rating)) return false;
        if (!Objects.equals(specialFeatures, film.specialFeatures))
            return false;
        return Objects.equals(lastUpdate, film.lastUpdate);
    }

    @Override
    public int hashCode() {
        int result = filmId != null ? filmId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        //result = 31 * result + (originalLanguageId != null ? originalLanguageId.hashCode() : 0);
        result = 31 * result + (rentalDuration != null ? rentalDuration.hashCode() : 0);
        result = 31 * result + (rentalRate != null ? rentalRate.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (replacementCost != null ? replacementCost.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (specialFeatures != null ? specialFeatures.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }
}
