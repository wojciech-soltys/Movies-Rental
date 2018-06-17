package com.epam.katowice.services;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.specifications.FilmSpecBuilder;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.mappers.FilmMapper;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper mapper = Selma.builder(FilmMapper.class).build();

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
        return filmRepository.findAll().stream().map(mapper::asFilmDto).collect(Collectors.toList());
    }

    @Override
    public Page<FilmDto> getByPredicate(Filters filters, Pageable pageable) {
        FilmSpecBuilder filmSpecBuilder = new FilmSpecBuilder();
        Page<Film> entityPage = filmRepository.findAll(filmSpecBuilder.toSpecification(filters), pageable);
        List<FilmDto> films = entityPage.getContent().stream().map(mapper::asFilmDto).collect(Collectors.toList());
        return new PageImpl<>(films, pageable, entityPage.getTotalElements());
    }

    @Override
    public FilmDto findById(Long id) {
        return mapper.asFilmDto(filmRepository.findById(id).get());
    }

    @Override
    public FilmDto save(FilmDto film) {
        return mapper.asFilmDto(filmRepository.save(mapper.asFilm(film,new Film())));
    }

}