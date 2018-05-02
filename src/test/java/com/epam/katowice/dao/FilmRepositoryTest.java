package com.epam.katowice.dao;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.domain.Category;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.specifications.FilmSpecBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class FilmRepositoryTest extends MovieRentalTest {

    @Autowired
    private FilmRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testGetFilmsCount() {
        //given
        Film film = new Film("AAA");
        repository.save(film);
        film = new Film("BBB");
        repository.save(film);

        //when
        long movieCount = repository.count();

        //than
        assertThat(movieCount).isEqualTo(2l);
    }

    @Test
    @Transactional
    public void testGetAllFilms() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        //when
        List<Film> films = repository.findAll();

        //than
        assertThat(films.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void testGetFilmsPageOne() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.DESC, "title");

        //when
        Page<Film> page = repository.findAll(pageRequest);

        //than
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title2");
    }

    @Test
    @Transactional
    public void testGetFilmsPageTwo() {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        repository.save(film1);
        Film film2 = new Film(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        repository.save(film2);

        Pageable pageRequest = new PageRequest(0,10, Sort.Direction.ASC, "title");

        //when
        Page<Film> page = repository.findAll(pageRequest);

        //than
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    @Transactional
    public void testFindFilmsOne() {
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
        assertThat(page.getContent().size()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    @Transactional
    public void testFindFilmsTwo() {
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
        assertThat(page.getContent().size()).isEqualTo(0);
    }



    @Test
    @Transactional
    public void testFindByIdOne() {
        //given
        Film film1 = new Film(null, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        film1 = repository.save(film1);
        Film film2 = new Film(null, "title2", "description2", 2016, new Integer(200), Rating.NC17);
        film2 = repository.save(film2);

        //when
        Optional<Film> repoFilm1 = repository.findById(film1.getId());
        Optional<Film> repoFilm2 = repository.findById(film2.getId());

        //than
        assertThat(film1.getId()).isEqualTo(repoFilm1.get().getId());
        assertThat(film1.getTitle()).isEqualTo(repoFilm1.get().getTitle());

        assertThat(film2.getId()).isEqualTo(repoFilm2.get().getId());
        assertThat(film2.getTitle()).isEqualTo(repoFilm2.get().getTitle());
    }

    @Test
    @Transactional
    public void testAddMovie () {
        //given
        Film film1 = new Film(1L, "title1", "description1", 2014, new Integer(100), Rating.NC17);
        Category category = new Category();
        category.setCategoryID(new Long(1));
        category.setName("Action");
        categoryRepository.save(category);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        film1.setCategories(categories);
        film1 = repository.save(film1);

        //when
        Film repoFilm = repository.findById(film1.getId()).get();

        //then
        assertThat(film1.getId()).isEqualTo(repoFilm.getId());
        assertThat(film1.getTitle()).isEqualTo(repoFilm.getTitle());
        assertThat(film1.getDescription()).isEqualTo(repoFilm.getDescription());
        assertThat(film1.getReleaseYear()).isEqualTo(repoFilm.getReleaseYear());
        assertThat(film1.getLength()).isEqualTo(repoFilm.getLength());
        assertThat(film1.getRating()).isEqualTo(repoFilm.getRating());
        assertThat(film1.getCategories().size()).isEqualTo(1);
        assertThat(film1.getCategories().iterator().next().getName()).isEqualTo("Action");
    }
}