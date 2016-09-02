package com.epam.katowice.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "film_id")
    private Long id;

    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    private Integer length;

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @ManyToMany
    @JoinTable(name="film_category",
            joinColumns={@JoinColumn(name="film_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")})
    private Set<Category> categories;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="language_id")
    private Language language;

    @ManyToMany
    @JoinTable(name="film_actor",
            joinColumns={@JoinColumn(name="film_id")},
            inverseJoinColumns={@JoinColumn(name="actor_id")})
    private Set<Actor> actors;

    @OneToOne(mappedBy = "film")
    private FilmText filmText;


    public Film() {

    }

    public Film(Long film_id, String title, String description, Integer releaseYear, Integer length, Rating rating) {
        this.id = film_id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.rating = rating;
    }

    public Film(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public FilmText getFilmText() {
        return filmText;
    }

    public void setFilmText(FilmText filmText) {
        this.filmText = filmText;
    }
}
