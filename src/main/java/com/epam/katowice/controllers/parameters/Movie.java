package com.epam.katowice.controllers.parameters;

import com.epam.katowice.entities.Category;
import com.epam.katowice.entities.Language;
import com.epam.katowice.entities.Rating;
import com.epam.katowice.entities.RatingConverter;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojciech_Soltys on 05.09.2016.
 */
public class Movie {

    private String title;

    private String description;

    private Integer releaseYear;

    private Integer length;

    private Language language;

    private List<Category> categories = new ArrayList<Category>();

    @Convert(converter = RatingConverter.class)
    private Rating rating;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
