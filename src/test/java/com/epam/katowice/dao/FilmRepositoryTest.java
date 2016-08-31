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
        Film film = new Film("AAA");
        repository.save(film);
        film = new Film("BBB");
        repository.save(film);

        long movieCount = repository.count();

        Assertions.assertThat(movieCount).isEqualTo(2l);
    }

    @Test
    public void testGetAllFilms() throws Exception {
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        List<Film> films = repository.findAll();

        Assertions.assertThat(films.size()).isEqualTo(2);
    }

    @Test
    public void testGetFilmsPage() throws Exception {
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");
        Pageable pageRequest1 = new PageRequest(0,10, Sort.Direction.ASC, "title");

        Page<Film> page = repository.findAll(pageRequest);
        Assertions.assertThat(page.getContent().size()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title2");

        page = repository.findAll(pageRequest1);
        Assertions.assertThat(page.getContent().size()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    public void testFindFilms() throws Exception {
        Film film1 = new Film(1L, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        FilmSpecBuilder filmSpecBuilder = new FilmSpecBuilder();
        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");
        Filters filters = new Filters();
        filters.setTitle("title1");

        Page<Film> page = repository.findAll(filmSpecBuilder.toSpecification(filters),pageRequest);
        Assertions.assertThat(page.getContent().size()).isEqualTo(1);
        Assertions.assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");

        filters.setTitle("title2");
        page = repository.findAll(filmSpecBuilder.toSpecification(filters),pageRequest);
        Assertions.assertThat(page.getContent().size()).isEqualTo(0);
    }
}