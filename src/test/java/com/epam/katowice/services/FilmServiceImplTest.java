package com.epam.katowice.services;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public class FilmServiceImplTest extends MovieRentalTest {

    @Mock
    private FilmRepository repository;

    private FilmService filmService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        filmService = new FilmServiceImpl(repository);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFilmsCount() throws Exception {
        // when
        Mockito.when(repository.count()).thenReturn(1000l);

        // then
        long movieCount = filmService.getFilmsCount();

        //expected
        Assertions.assertThat(movieCount).isEqualTo(1000l);
    }


    @Test
    public void testGetAllFilms() throws Exception {
        // when
        Film film1 = new Film(1L, "title1", "description1", 2016, 100);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, 200);
        repository.save(film2);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(film1, film2));

        // then
        List<FilmDto> films = filmService.getAllFilms();

        //expected
        Assertions.assertThat(films.size()).isEqualTo(2);

        FilmDto FilmDto1 = films.get(0);
        FilmDto FilmDto2 = films.get(1);

        EqualsBuilder.reflectionEquals(film1, FilmDto1);

        //Id
        Assertions.assertThat(film1.getFilm_id()).isEqualTo(FilmDto1.getFilm_id());
        Assertions.assertThat(film2.getFilm_id()).isEqualTo(FilmDto2.getFilm_id());

        //Title
        Assertions.assertThat(film1.getTitle()).isEqualTo(FilmDto1.getTitle());
        Assertions.assertThat(film2.getTitle()).isEqualTo(FilmDto2.getTitle());

        //Description
        Assertions.assertThat(film1.getDescription()).isEqualTo(FilmDto1.getDescription());
        Assertions.assertThat(film2.getDescription()).isEqualTo(FilmDto2.getDescription());

        //Release_Year
        Assertions.assertThat(film1.getRelease_year()).isEqualTo(FilmDto1.getRelease_year());
        Assertions.assertThat(film2.getRelease_year()).isEqualTo(FilmDto2.getRelease_year());

        //Length
        Assertions.assertThat(film1.getLength()).isEqualTo(FilmDto1.getLength());
        Assertions.assertThat(film2.getLength()).isEqualTo(FilmDto2.getLength());
    }

}