package com.epam.katowice.services;

import com.epam.katowice.dao.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
