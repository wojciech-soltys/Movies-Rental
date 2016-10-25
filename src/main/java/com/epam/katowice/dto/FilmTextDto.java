package com.epam.katowice.dto;

/**
 * Created by Wojciech_Soltys on 25.10.2016.
 */
public class FilmTextDto {

    private Long id;

    private String title;

    private String description;

    public FilmTextDto() {
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
}
