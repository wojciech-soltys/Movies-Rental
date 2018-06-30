package com.epam.katowice.controllers;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.domain.Actor;
import com.epam.katowice.domain.Category;
import com.epam.katowice.domain.Language;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.services.ActorService;
import com.epam.katowice.services.CategoryService;
import com.epam.katowice.services.FilmService;
import com.epam.katowice.services.LanguageService;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.Filter;
import java.util.Arrays;

import static com.epam.katowice.controllers.MovieRentalController.DETAILS_ENDPOINT;
import static com.epam.katowice.controllers.MovieRentalController.NEW_MOVIE_VIEW;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class MovieRentalControllerTest extends MovieRentalTest {

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Mock
    private FilmService filmService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private LanguageService languageService;

    @Mock
    private ActorService actorService;

    @InjectMocks
    private MovieRentalController movieController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new HateoasPageableHandlerMethodArgumentResolver())
                .setViewResolvers((ViewResolver) (viewName, locale) -> new MappingJackson2JsonView())
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @AfterMethod
    public void tearDown() {

    }

    @Test
    public void testGetFilmsCount() {
        // given
        when(filmService.getFilmsCount()).thenReturn(1000l);
        ExtendedModelMap model = new ExtendedModelMap();

        // when
        String index = movieController.getFilmsCount(model);

        //than
        Assertions.assertThat(index).isEqualTo("index");
        Assertions.assertThat(model.asMap()).isNotEmpty();
        Assertions.assertThat(model.asMap()).containsKey("movieCount");
        Assertions.assertThat(model.asMap().get("movieCount")).isInstanceOf(Long.class);
        Assertions.assertThat((Long)model.asMap().get("movieCount")).isEqualTo(1000l);
    }

    @Test
    public void testGetFilms() throws Exception {
        //given
        prepareListOfMovies();

        //then
        mockMvc.perform(get("/movies")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
            .andExpect(model().attribute("page", hasProperty("content", hasSize(2))))
            .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("id", is(1L)),
                    hasProperty("description", is("description1")),
                    hasProperty("title", is("title1")),
                    hasProperty("releaseYear", is(2016)),
                    hasProperty("length", is(100))
                )
            ))))
           .andExpect(model().attribute("page", hasProperty("content", hasItem(
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("description", is("description2")),
                    hasProperty("title", is("title2")),
                    hasProperty("releaseYear", is(2016)),
                    hasProperty("length", is(200))
                )
            ))));
    }

    @Test
    public void testGetFilmById() throws Exception {
        //given, when
        when(filmService.findById(Mockito.any(Long.class))).thenReturn(prepareMovie());
        prepareDictionaries();

        mockMvc.perform(get(DETAILS_ENDPOINT ).param("id","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("film", allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("description", is("description1")),
                                hasProperty("title", is("title1")),
                                hasProperty("releaseYear", is(2016)),
                                hasProperty("length", is(100))
                        )));
    }

    @Test
    public void testAddMovie() throws Exception {
        //given, when
        when(filmService.save(Mockito.any(FilmDto.class))).thenReturn(prepareMovie());
        prepareDictionaries();

        //then
        mockMvc.perform(get(MovieRentalController.NEW_MOVIE_ENDPOINT)
                .with(user("admin").roles("ADMIN"))
                .param("title", "TEST123"))
                .andExpect(status().isOk())
                .andExpect(view().name(NEW_MOVIE_VIEW));
    }

    private FilmDto prepareMovie() {
        //given
        return new FilmDto(1L, "title1", "description1", 2016,
                new Integer(100), Rating.NC17);
    }

    private void prepareDictionaries() {
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category()));
        when(languageService.findAll()).thenReturn(Arrays.asList(new Language()));
        when(actorService.findAll()).thenReturn(Arrays.asList(new Actor()));
    }

    @Test
    public void testUserShouldNotHaveAccessToAddMovie() throws Exception {
        mockMvc.perform(get(MovieRentalController.NEW_MOVIE_ENDPOINT)
                .with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAdminShouldHaveAccessToAddMovie() throws Exception {
        mockMvc.perform(get(MovieRentalController.NEW_MOVIE_ENDPOINT)
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void testAllUsersShouldHaveAccessToIndex() throws Exception {
        mockMvc.perform(get(MovieRentalController.INDEX_ENDPOINT))
                .andExpect(status().isOk());
    }

    @Test
    public void testAdminShouldHaveAccessToMovieList() throws Exception {
        //given, when
        prepareListOfMovies();

        //then
        mockMvc.perform(get(MovieRentalController.MOVIES_LIST_ENDPOINT)
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    private void prepareListOfMovies() {
        //given
        FilmDto film1 = new FilmDto(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        FilmDto film2 = new FilmDto(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);

        //when
        when(filmService.getByPredicate(Mockito.any(Filters.class), Mockito.any(Pageable.class))).
                thenReturn(new PageImpl<>(Arrays.asList(film1,film2)));
        prepareDictionaries();
    }

    @Test
    public void testUserShouldHaveAccessToMovieList() throws Exception {
        //given, when
        prepareListOfMovies();

        //then
        mockMvc.perform(get(MovieRentalController.MOVIES_LIST_ENDPOINT)
                .with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    public void testNoAuthenticatedUserShouldNotHaveAccessToMovieList() throws Exception {
        //given, when
        prepareListOfMovies();

        //then
        mockMvc.perform(get(MovieRentalController.MOVIES_LIST_ENDPOINT)
                .with(user("user").roles("OTHER")))
                .andExpect(status().isForbidden());
    }
}