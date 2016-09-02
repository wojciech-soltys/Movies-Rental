package com.epam.katowice.services;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import com.epam.katowice.entities.specifications.FilmSpecBuilder;
import com.epam.katowice.mappers.FilmMapper;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
@Service
public class FilmServiceImpl implements FilmService {

    private static final int PAGE_SIZE = 50;
    private final FilmRepository filmRepository;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public long getFilmsCount() {
        return filmRepository.count();
    }

    @Override
    public List<FilmDto> getAllFilms() {
        FilmMapper mapper = Selma.builder(FilmMapper.class).build();
        return filmRepository.findAll().stream().map(mapper::asFilmDto).collect(Collectors.toList());
    }

    @Override
    public Page<Film> getByPredicate(Filters filters, Pageable pageable) {

        FilmSpecBuilder filmSpecBuilder = new FilmSpecBuilder();

        return filmRepository.findAll(filmSpecBuilder.toSpecification(filters), pageable);
    }

}
