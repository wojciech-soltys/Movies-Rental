package com.epam.katowice.services;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.dto.FilmDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public class FilmServiceImplTest extends MovieRentalTest {

    @Mock
    private FilmRepository repository;

    private FilmService filmService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filmService = new FilmServiceImpl(repository);
    }

    @AfterMethod
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testGetFilmsCount() {
        // given
        when(repository.count()).thenReturn(1000l);

        // when
        long movieCount = filmService.getFilmsCount();

        // than
        assertThat(movieCount).isEqualTo(1000l);
    }


    @Test
    public void testGetAllFilms() {
        // given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);

        when(repository.findAll()).thenReturn(Arrays.asList(film1, film2));

        // when
        List<FilmDto> films = filmService.getAllFilms();

        // than
        assertThat(films.size()).isEqualTo(2);

        FilmDto filmDto1 = films.get(0);
        FilmDto filmDto2 = films.get(1);

        EqualsBuilder.reflectionEquals(film1, filmDto1);

        //Id
        assertThat(film1.getId()).isEqualTo(filmDto1.getId());
        assertThat(film2.getId()).isEqualTo(filmDto2.getId());

        //Title
        assertThat(film1.getTitle()).isEqualTo(filmDto1.getTitle());
        assertThat(film2.getTitle()).isEqualTo(filmDto2.getTitle());

        //Description
        assertThat(film1.getDescription()).isEqualTo(filmDto1.getDescription());
        assertThat(film2.getDescription()).isEqualTo(filmDto2.getDescription());

        //Release_Year
        assertThat(film1.getReleaseYear()).isEqualTo(filmDto1.getReleaseYear());
        assertThat(film2.getReleaseYear()).isEqualTo(filmDto2.getReleaseYear());

        //Length
        assertThat(film1.getLength()).isEqualTo(filmDto1.getLength());
        assertThat(film2.getLength()).isEqualTo(filmDto2.getLength());
    }


    @Test
    public void testGetPageOfFilmsDesc() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        when(repository.findAll(pageRequest)).thenReturn(new PageImpl<>(Arrays.asList(film1, film2)));

        // when
        Page<Film> page = repository.findAll(pageRequest);

        //than
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    public void testGetPageOfFilmsAsc() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest1 = new PageRequest(0,10, Sort.Direction.ASC, "title");

        when(repository.findAll(pageRequest1)).thenReturn(new PageImpl<>(Arrays.asList(film2,film1)));

        // when
        Page<Film> page2 = repository.findAll(pageRequest1);

        //than
        assertThat(page2.getContent().size()).isEqualTo(2);
        assertThat(page2.getContent().get(0).getTitle()).isEqualTo("title2");
    }

    @Test
    public void testFindFilmsOne() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        Filters filters = new Filters();
        filters.setTitle("title1");

        when(repository.findAll(Mockito.any(Specification.class),Mockito.eq(pageRequest)))
                .thenReturn(new PageImpl<>(Arrays.asList(film1)));

        //when
        Page<FilmDto> page = filmService.getByPredicate(filters, pageRequest);

        //than
        assertThat(page.getContent().size()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    public void testFindFilmsTwo() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);
        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        Filters filters = new Filters();
        filters.setCategory("title2");

        when(repository.findAll(Mockito.any(Specification.class),Mockito.eq(pageRequest)))
                .thenReturn(new PageImpl<>(Arrays.asList(film2)));

        //when
        Page<FilmDto> page = filmService.getByPredicate(filters, pageRequest);

        //than
        assertThat(page.getContent().size()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title2");
    }

    @Test
    public void testFindByIdOne() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(film1));

        //when
        FilmDto returnFilm = filmService.findById(1L);

        //than
        assertThat(returnFilm.getId()).isEqualTo(film1.getId());
        assertThat(returnFilm.getTitle()).isEqualTo(film1.getTitle());
    }

    @Test
    public void testFindByIdTwo() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        //repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        //repository.save(film2);

        when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(film2));

        //when
        FilmDto returnFilm = filmService.findById(2L);

        //than
        assertThat(returnFilm.getId()).isEqualTo(film2.getId());
        assertThat(returnFilm.getTitle()).isEqualTo(film2.getTitle());
    }

    @Test
    public void testSaveFilm() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        FilmDto filmDto1 = new FilmDto(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);

        when(repository.save(Mockito.any(Film.class))).thenReturn(film1);

        //when
        FilmDto film2 = filmService.save(filmDto1);

        //then
        assertThat(film2.getId()).isEqualTo(filmDto1.getId());
        assertThat(film2.getTitle()).isEqualTo(filmDto1.getTitle());
        assertThat(film2.getRating()).isEqualTo(filmDto1.getRating());
    }

}