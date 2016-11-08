package com.epam.katowice.mappers;

import com.epam.katowice.dto.FilmForm;
import com.epam.katowice.entities.Film;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
@Mapper
public interface FilmMapper {
    @Maps(withIgnoreFields="com.epam.katowice.entities.FilmText.film")
    FilmForm asFilmDto(Film in);
    @Maps(withIgnoreFields="com.epam.katowice.entities.FilmText.film")
    Film asFilm(FilmForm in, Film out);
}
