package com.epam.katowice.dao;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.entities.Film;
import com.epam.katowice.entities.Rating;
import com.epam.katowice.entities.specifications.FilmSpecBuilder;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class FilmRepositoryTest extends MovieRentalTest {

    @Autowired
    private FilmRepository repository;

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void testGetFilmsCount() throws Exception {
        //given
        Film film = new Film("AAA");
        repository.save(film);
        film = new Film("BBB");
        repository.save(film);

        //when
        long movieCount = repository.count();

        //than
        Assertions.assertThat(movieCount).isEqualTo(2l);
    }

    @Test
    @Transactional
    public void testGetAllFilms() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        //when
        List<Film> films = repository.findAll();

        //than
        Assertions.assertThat(films.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void testGetFilmsPageOne() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        //when
        Page<Film> page = repository.findAll(pageRequest);

        //than
        Assertions.assertThat(page.getContent().size()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title2");
    }

    @Test
    @Transactional
    public void testGetFilmsPageTwo() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.ASC, "title");

        //when
        Page<Film> page = repository.findAll(pageRequest);

        //than
        Assertions.assertThat(page.getContent().size()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    @Transactional
    public void testFindFilmsOne() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        FilmSpecBuilder filmSpecBuilder = new FilmSpecBuilder();
        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");
        Filters filters = new Filters();
        filters.setTitle("title1");

        //when
        Page<Film> page = repository.findAll(filmSpecBuilder.toSpecification(filters),pageRequest);

        //than
        Assertions.assertThat(page.getContent().size()).isEqualTo(1);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    @Transactional
    public void testFindFilmsTwo() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        FilmSpecBuilder filmSpecBuilder = new FilmSpecBuilder();
        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        Filters filters = new Filters();
        filters.setTitle("title2");

        //when
        Page<Film>page = repository.findAll(filmSpecBuilder.toSpecification(filters),pageRequest);

        //than
        Assertions.assertThat(page.getContent().size()).isEqualTo(0);
    }



    @Test
    @Transactional
    public void testFindByIdOne() throws Exception {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        //when
        Film repoFilm1 = repository.findById(1L);
        Film repoFilm2 = repository.findById(2L);

        //than
        Assertions.assertThat(film1.getId()).isEqualTo(repoFilm1.getId());
        Assertions.assertThat(film1.getTitle()).isEqualTo(repoFilm1.getTitle());

        Assertions.assertThat(film2.getId()).isEqualTo(repoFilm2.getId());
        Assertions.assertThat(film2.getTitle()).isEqualTo(repoFilm2.getTitle());
    }
}