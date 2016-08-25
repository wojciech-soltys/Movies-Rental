package com.epam.katowice.entities;

import java.sql.Date;
import javax.persistence.*;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long film_id;

    private String title;

    @Column(name = "description")
    private String description;

    private int release_year;

    private Integer length;

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    public Film() {

    }

    public Film(Long film_id, String title, String description, int release_year, Integer length) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
        this.release_year = release_year;
        this.length = length;
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

    public Long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Long film_id) {
        this.film_id = film_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
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
}
