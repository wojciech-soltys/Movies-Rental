package com.epam.katowice.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
@Data
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private Long languageID;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    private Date lastUpdate;

}
