package com.epam.katowice.mappers;

import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import fr.xebia.extras.selma.Mapper;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
@Mapper
public interface FilmMapper {
    FilmDto asFilmDto(Film in);
}
