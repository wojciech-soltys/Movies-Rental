package com.epam.katowice.services;

import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public interface FilmService {
    long getFilmsCount();
    List<FilmDto> getAllFilms();
}
