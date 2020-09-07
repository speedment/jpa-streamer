package com.speedment.jpastreamer.fieldgenerator.test;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "language", schema = "sakila")
public class Language implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", nullable = false, updatable = false, columnDefinition = "tinyint(3)")
    private Integer languageId;

    @Column(name = "name", nullable = false, columnDefinition = "char(20)")
    private String name;

    @OneToMany(mappedBy = "language")
    private Set<Film> films;

    @ManyToMany
    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer language_id) {
        this.languageId = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @Override
    public String toString() {
        return "Language{" +
                "languageId=" + languageId +
                ", name='" + name + '\'' +
                '}';
    }

}
