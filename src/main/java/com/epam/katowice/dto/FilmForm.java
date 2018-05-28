package com.epam.katowice.dto;

import com.epam.katowice.domain.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class FilmForm {

    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @Size(max = 65535)
    private String description;

    @Min(value = 1895)
    private int releaseYear;

    @NotNull
    private Integer length;

    @NotNull
    private Rating rating;

    @NotNull
    private Language language;

    private FilmTextDto filmText;

    private Set<Features> specialFeatures = new HashSet<>();

    private Set<Actor> actors = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public FilmForm() {
        this.releaseYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    public FilmForm(Long film_id, String title, String description, Integer releaseYear, Integer length, Rating rating) {
        this.id = film_id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public FilmTextDto getFilmText() {
        return filmText;
    }

    public void setFilmText(FilmTextDto filmText) {
        this.filmText = filmText;
    }

    public Set<Features> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(Set<Features> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }
}
