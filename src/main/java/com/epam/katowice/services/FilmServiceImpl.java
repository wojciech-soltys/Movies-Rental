package com.epam.katowice.services;

import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import com.epam.katowice.mappers.FilmMapper;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
@Service
public class FilmServiceImpl implements FilmService {

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
}
