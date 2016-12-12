package com.epam.katowice.domain;

import javax.persistence.*;

/**
 * Created by Wojciech_Soltys on 01.09.2016.
 */
@Entity
public class FilmText {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "film_id")
    private Long id;

    private String title;

    private String description;

    @OneToOne
    @JoinColumn(name = "film_id")
    private Film film;

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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
