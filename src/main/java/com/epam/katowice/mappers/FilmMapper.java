package com.epam.katowice.mappers;

import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.domain.Film;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
@Mapper
public interface FilmMapper {
    @Maps(withIgnoreFields="com.epam.katowice.domain.FilmText.film")
    FilmDto asFilmDto(Film in);
    @Maps(withIgnoreFields="com.epam.katowice.domain.FilmText.film")
    Film asFilm(FilmDto in, Film out);
}
