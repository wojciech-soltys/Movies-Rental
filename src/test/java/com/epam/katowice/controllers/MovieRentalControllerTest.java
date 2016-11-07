package com.epam.katowice.controllers;

import com.epam.katowice.common.MovieRentalTest;
import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.*;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Wojciech_Soltys on 10.08.2016.
 */
public class MovieRentalControllerTest extends MovieRentalTest {

    @Autowired
    WebApplicationContext webCtx;

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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new HateoasPageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFilmsCount() throws Exception {
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
        FilmDto film1 = new FilmDto(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);
        FilmDto film2 = new FilmDto(2L, "title2", "description2", 2016, new Integer(200), Rating.NC17);

        //when
        when(filmService.getByPredicate(Mockito.any(Filters.class), Mockito.any(Pageable.class))).
                thenReturn(new PageImpl<FilmDto>(Arrays.asList(film1,film2)));
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category()));
        when(languageService.findAll()).thenReturn(Arrays.asList(new Language()));

        //then
        mockMvc.perform(get("/movies"))
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
        Film film1 = new Film(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);

        when(filmService.findById(Mockito.any(Long.class))).thenReturn(film1);
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category()));
        when(languageService.findAll()).thenReturn(Arrays.asList(new Language()));

        mockMvc.perform(get("/movie/view/{id}", 1L))
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
        FilmDto film1 = new FilmDto(1L, "title1", "description1", 2016, new Integer(100), Rating.NC17);

        when(filmService.save(Mockito.any(FilmDto.class))).thenReturn(film1);
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category()));
        when(languageService.findAll()).thenReturn(Arrays.asList(new Language()));
        when(actorService.findAll()).thenReturn(Arrays.asList(new Actor()));

        mockMvc.perform(get("/addMovie")
                .param("title", "TEST123"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:viewAddMovie"))
                .andExpect(model().attribute("movie", allOf(
                        hasProperty("description", is("description1")),
                        hasProperty("title", is("title1")),
                        hasProperty("releaseYear", is(2016)),
                        hasProperty("length", is(100))
                )));
    }
}