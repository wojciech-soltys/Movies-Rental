package com.epam.katowice.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long language_id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    private Date last_update;

    public Long getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(Long language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
