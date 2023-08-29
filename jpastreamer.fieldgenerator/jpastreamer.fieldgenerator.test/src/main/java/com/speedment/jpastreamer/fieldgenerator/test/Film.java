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
package com.speedment.jpastreamer.fieldgenerator.test;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "film", schema = "sakila")
public class Film {

    // Reproduce #58
    private static final Map<Class<? extends Film>, List<String>> A = new HashMap<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false, unique = true, updatable = false, columnDefinition = "smallint(5)")
    // Make this field package private just for good measure...
    int filmId;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotBlank
    private String description;

    @Column(name = "audienceScore", nullable = true, columnDefinition = "decimal(4,2)")
    private Double audienceScore;

    @Lob
    @Column(name = "cover", nullable = true)
    private byte[] cover;

    @Lob
    @Column(name = "long_description", nullable = true)
    private String long_description;

    /**
     * DATE AND TIME
     */
    @Column(name = "release_date", nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private java.util.Date releaseDate;

    @Column(name = "release_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar releaseTimestamp;

    @Column(name = "release_time", nullable = false, columnDefinition = "TIME")
    private Time releaseTime;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Column(name = "complex_column")
    @NotBlank
    @NotEmpty
    @NotNull
    private Map<Map<List<Set<Double>>, Integer>, Long> complexColumn;

    @Column(name = "rental_duration", columnDefinition = "smallint(5)")
    private Integer rentalDuration;

    @Column(name = "rental_rate", columnDefinition = "decimal(4,2)")
    @NotNull
    @NotEmpty
    private Float rentalRate;

    @Column(name = "length", columnDefinition = "smallint(5)")
    @NotNull
    @NotEmpty
    private Integer length;

    @Column(name = "replacement_cost", columnDefinition = "decimal(5,2)")
    private Float replacementCost;

    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    private String rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")    
    @NotNull
    @NotEmpty
    private Set<String> specialFeatures; // Should be Set<String>

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
/*
    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }*/


    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setRentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
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

    public Set<String> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(Set<String> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }


    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Double getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(Double audienceScore) {
        this.audienceScore = audienceScore;
    }

    public Map<Map<List<Set<Double>>, Integer>, Long> getComplexColumn() {
        return complexColumn;
    }

    public void setComplexColumn(Map<Map<List<Set<Double>>, Integer>, Long> complexColumn) {
        this.complexColumn = complexColumn;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    // This getter is using another format
    // where underscores are removed and
    // camelized
    public String getLongDescription() {
        return long_description;
    }

    public void setLongDescription(String long_description) {
        this.long_description = long_description;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", audienceScore=" + audienceScore +
                ", cover=" + Arrays.toString(cover) +
                ", long_description='" + long_description + '\'' +
                ", releaseDate=" + releaseDate +
                ", releaseTimestamp=" + releaseTimestamp +
                ", releaseTime=" + releaseTime +
                ", language=" + language +
                ", originalLanguage=" + originalLanguage +
                ", complexColumn=" + complexColumn +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating='" + rating + '\'' +
                ", specialFeatures=" + specialFeatures +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public java.util.Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(java.util.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Calendar getReleaseTimestamp() {
        return releaseTimestamp;
    }

    public void setReleaseTimestamp(Calendar releaseTimestamp) {
        this.releaseTimestamp = releaseTimestamp;
    }

    public Time getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Time releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRating() {
        return rating;
    }

    public int getSomeOtherPropertyThatIsNotAField() {
        return 0;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // This method could be added via an annotation
    // processor if the field is private.
    String long_descriptionHelper() {
        return  long_description;
    }

}
