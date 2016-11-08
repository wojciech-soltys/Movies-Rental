package com.epam.katowice.services;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dto.FilmForm;
import com.epam.katowice.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public interface FilmService {
    long getFilmsCount();
    List<FilmForm> getAllFilms();
    Page<FilmForm> getByPredicate(Filters filters, Pageable pageable);
    Film findById(Long id);
    FilmForm save(FilmForm film);
}
