package com.epam.katowice.dto;

import com.epam.katowice.entities.Actor;
import com.epam.katowice.entities.Category;
import com.epam.katowice.entities.Language;
import com.epam.katowice.entities.Rating;

import java.util.Set;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class FilmDto {

    private Long film_id;

    private String title;

    private String description;

    private int releaseYear;

    private Integer length;

    private Rating rating;

    private Set<Category> categories;

    private Language language;

    private Set<Actor> actors;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public FilmDto() {

    }

    public FilmDto(Long film_id, String title, String description, int releaseYear, Integer length) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    public Long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Long film_id) {
        this.film_id = film_id;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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
}
