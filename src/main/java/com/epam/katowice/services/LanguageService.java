package com.epam.katowice.services;

import com.epam.katowice.entities.Language;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 26.08.2016.
 */
public interface LanguageService {
    List<Language> findAll();
}
