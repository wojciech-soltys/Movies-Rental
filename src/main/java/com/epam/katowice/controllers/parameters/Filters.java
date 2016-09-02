package com.epam.katowice.controllers.parameters;

/**
 * Created by Wojciech_Soltys on 29.08.2016.
 */
public class Filters {

    private String title;
    private Integer release_year_from = new Integer(1950);
    private Integer release_year_to = new Integer(2016);
    private String category;
    private String language;
    private Integer maxLength = new Integer(180);
    private String actor;

    public Filters() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRelease_year_from() {
        return release_year_from;
    }

    public void setRelease_year_from(Integer release_year_from) {
        this.release_year_from = release_year_from;
    }

    public Integer getRelease_year_to() {
        return release_year_to;
    }

    public void setRelease_year_to(Integer release_year_to) {
        this.release_year_to = release_year_to;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
