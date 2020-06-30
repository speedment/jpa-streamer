package com.speedment.jpastreamer.fieldgenerator.test;

import com.speedment.jpastreamer.fieldgenerator.standard.component.FilmRating;
import com.speedment.jpastreamer.fieldgenerator.standard.component.FilmTitle;
import com.speedment.jpastreamer.fieldgenerator.standard.component.FilmTitleConverter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "film", schema = "sakila")
public class Film
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false, unique = true, updatable = false, columnDefinition = "smallint(5)")
    private int filmId;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = FilmTitleConverter.class)
    private FilmTitle title;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "audienceScore", nullable = true, columnDefinition = "decimal(4,2)")
    private Double audienceScore;

    @Column(name = "random_map", nullable = false)
    private Map<FilmTitle, Integer> randomMap;

    @Lob
    @Column(name = "cover", nullable = true)
    private byte[] cover;

    @Lob
    @Column(name = "long_description", nullable = true)
    private String long_description;

    /** DATE AND TIME */
    @Column(name = "release_date", nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private java.util.Date releaseDate;

    @Column(name = "release_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar releaseTimestamp;

    @Column(name = "release_time", nullable = false, columnDefinition = "TIME")
    private Time releaseTime;


    /*@ManyToOne
     @JoinColumn(name = "language_id")
     private Language language;*/
/*
     @ManyToOne
     @JoinColumn(name = "original_language_id")
     private Language originalLanguage;
*/

    @Column(name = "complex_column")
    private Map<Map<List<Set<Double>>,Integer>,Long> complexColumn;

    @Column(name = "complex_film_rating")
    private FilmRating[][][][][][][][] complexFilmRating;

    @Column(name = "film_ratings")
    private Set<FilmRating[]> filmRatings;

    @Column(name = "rental_duration", columnDefinition = "smallint(5)")
    private Integer rentalDuration;

    @Column(name = "rental_rate", columnDefinition = "decimal(4,2)")
    private Float rentalRate;

    @Column(name = "length", columnDefinition = "smallint(5)")
    private Integer length;

    @Column(name = "replacement_cost", columnDefinition = "decimal(5,2)")
    private Float replacementCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
    private FilmRating rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
    private Set<String> specialFeatures; // Should be Set<String>

    @Column(name = "last_update", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime lastUpdate;


    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public FilmTitle getTitle() {
        return title;
    }

    public void setTitle(FilmTitle title) {
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

    /*
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
    */


    public Integer getRentalDuration() {
        Class<FilmTitleConverter> filmTitleConverterClass = FilmTitleConverter.class;
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

    public FilmRating getRating() {
        return rating;
    }

    public void setRating(FilmRating rating) {
        this.rating = rating;
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

    public Map<FilmTitle, Integer> getRandomMap() {
        return randomMap;
    }

    public void setRandomMap(Map<FilmTitle, Integer> randomMap) {
        this.randomMap = randomMap;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rentalDuration=" + rentalDuration +
                ", rentalRate=" + rentalRate +
                ", length=" + length +
                ", replacementCost=" + replacementCost +
                ", rating=" + rating +
                ", specialFeatures='" + specialFeatures + '\'' +
               // ", lastUpdate=" + lastUpdate +
                '}';
    }

    public FilmRating[][][][][][][][] getComplexFilmRating() {
        return complexFilmRating;
    }

    public void setComplexFilmRating(FilmRating[][][][][][][][] complexFilmRating) {
        this.complexFilmRating = complexFilmRating;
    }

    public Set<FilmRating[]> getFilmRatings() {
        return filmRatings;
    }

    public void setFilmRatings(Set<FilmRating[]> filmRatings) {
        this.filmRatings = filmRatings;
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
}
